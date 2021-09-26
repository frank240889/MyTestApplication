package com.franco.mytestapplication.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 2:10 p.m.
 */
/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 2:10 p.m.
 */
@Singleton
class AndroidViewModelFactory @Inject
/**
 * The constructor that it will provided with the Map that will cache the ViewModels, here was used
 * a [Provider] because offers many advantages for providing dependencies instead of provided T directly:
 * as follow:
 *
 * Compared to injecting {@code T} directly, injecting
 * {@code Provider<T>} enables:
 *
 * <ul>
 *   <li>retrieving multiple instances.</li>
 *   <li>lazy or optional retrieval of an instance.</li>
 *   <li>breaking circular dependencies.</li>
 *   <li>abstracting scope so you can look up an instance in a smaller scope
 *      from an instance in a containing scope.</li>
 * </ul>
 *
 * @param viewModelsMap The map in which the ViewModels will be cached.
 */
constructor(private val viewModelsMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModelsMap[modelClass]
            ?: error("model class $modelClass not found")
        return viewModelProvider.get() as T
    }
}