package com.franco.mytestapplication.presentation.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.franco.mytestapplication.dagger.AndroidViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 12:57 p.m.
 */
/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 12:57 p.m.
 */
abstract class ViewModelFragment<VM: ViewModel, VB: ViewBinding>: Fragment(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: AndroidViewModelFactory

    protected lateinit var viewModel:VM

    private var _viewBinding: VB? = null

    protected val viewBinding: VB = _viewBinding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = obtainViewBinding().apply {
        _viewBinding = this
    }.root

    abstract fun obtainViewModel(): VM

    abstract fun onLoading(loading: Boolean)

    abstract fun obtainViewBinding(): VB

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun androidInjector() = injector
}