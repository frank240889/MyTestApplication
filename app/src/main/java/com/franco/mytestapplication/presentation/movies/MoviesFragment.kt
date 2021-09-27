package com.franco.mytestapplication.presentation.movies

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.franco.mytestapplication.R
import com.franco.mytestapplication.databinding.MoviesFragmentBinding
import com.franco.mytestapplication.interfaces.ErrorHandler
import com.franco.mytestapplication.presentation.common.ViewModelFragment
import com.franco.mytestapplication.presentation.map.MapFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Base fragment for other screens.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 12:57 p.m.
 */
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
        viewBinding.flProgressContainer.visibility = if (loading) VISIBLE else GONE
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initToolbar()
        initExtendedFab()
        setupMovieList()
        setupAdapterMovies()
        initFetching()
    }

    @ExperimentalPagingApi
    private fun initFetching() {
        lifecycleScope.launch(fetchingJob) {
            viewModel.fetchMovies().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAdapterMovies() {
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
                        Snackbar.LENGTH_INDEFINITE).apply {
                            setAction(getString(R.string.retry)) {
                                adapter.retry()
                            }
                            behavior = object : BaseTransientBottomBar.Behavior() {
                                override fun canSwipeDismissView(child: View) = false

                            }
                            animationMode = Snackbar.ANIMATION_MODE_FADE
                    }.show()
                }
        }
    }

    private fun setupMovieList() {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
        viewBinding.rvMoviesList.apply {
            layoutManager = GridLayoutManager(requireActivity(), spanCount)
            adapter = this@MoviesFragment.adapter
        }
    }

    private fun provideMoviesAdapter() = MoviesAdapter()

    private fun initToolbar() {
        viewBinding.mtToolbarMovies.apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_movies_menu_upload_image -> {
                        TODO("No implemented yet.")
                    }
                }
                return@setOnMenuItemClickListener false
            }

            title = getString(R.string.title_movies)
        }
    }

    private fun initExtendedFab() {
        viewBinding.efabPlaces.setOnClickListener {
            MapFragment.newInstance().showNow(childFragmentManager, MapFragment::class.java.name)
        }
    }

}