package com.franco.mytestapplication.presentation.movies

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.franco.mytestapplication.R
import com.franco.mytestapplication.databinding.MoviesFragmentBinding
import com.franco.mytestapplication.interfaces.ErrorHandler
import com.franco.mytestapplication.presentation.common.ViewModelFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesFragment: ViewModelFragment<MoviesViewModel, MoviesFragmentBinding>() {

    @Inject
    lateinit var errorHandler: ErrorHandler

    private val adapter: MoviesAdapter by lazy {
        provideMoviesAdapter()
    }

    private var fetchingJob = Job()

    companion object {
        fun newInstance() = MoviesFragment()
    }

    override fun obtainViewBinding() = MoviesFragmentBinding.inflate(layoutInflater)

    override fun obtainViewModel() = ViewModelProvider(
        this,
        viewModelFactory
    )[MoviesViewModel::class.java]

    override fun onLoading(loading: Boolean) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
        viewBinding.rvMoviesList.apply {
            layoutManager = GridLayoutManager(requireActivity(), spanCount)
            adapter = this@MoviesFragment.adapter
        }

        adapter.addLoadStateListener { loadState ->

            onLoading(loadState.mediator?.append == LoadState.Loading)

            (
                    loadState.source.append as? LoadState.Error ?:
                    loadState.source.prepend as? LoadState.Error ?:
                    loadState.source.refresh as? LoadState.Error ?:
                    loadState.refresh as? LoadState.Error ?:
                    loadState.append as? LoadState.Error ?:
                    loadState.prepend as? LoadState.Error ?:
                    loadState.mediator?.append as? LoadState.Error ?:
                    loadState.mediator?.prepend as? LoadState.Error ?:
                    loadState.mediator?.refresh as? LoadState.Error
                    )?.let { err ->
                    val readableMessage = errorHandler.resolveError(err.error)
                    Snackbar.make(
                        viewBinding.root,
                        readableMessage,
                        Snackbar.LENGTH_LONG).apply {
                        setAction(getString(R.string.retry)) {
                            adapter.retry()
                        }
                        animationMode = Snackbar.ANIMATION_MODE_FADE
                    }.show()
                }
        }

        lifecycleScope.launch(fetchingJob) {
            /*viewModel.getPopularMovies().collectLatest {
                adapter.submitData(it)
            }*/
        }
    }

    private fun provideMoviesAdapter() =
        MoviesAdapter { movie ->
            findNavController()
                .navigate(
                    MoviesFragmentDirections
                        .actionNavigationMoviesToNavigationDetail()
                )
        }


}