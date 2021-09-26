package com.franco.mytestapplication.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 2:08 p.m.
 */
@Module
abstract class AndroidViewModelModule {

    /**
     * Associates a concrete object, in this case our [AndroidViewModelFactory] concrete implementation,
     * to an abstract [ViewModelProvider.Factory] because we are injecting an abstraction.
     */
    @Binds
    abstract fun bindAndroidViewModelFactory(androidViewModelFactory: AndroidViewModelFactory): ViewModelProvider.Factory

    /**
     * Creates a bind between the [AndroidViewModelFactory] and any [ViewModel] created.
     * We want [AndroidViewModelFactory] to depend on multiple ViewModels instead of just one.
     * Since our [AndroidViewModelFactory] wants a map of ViewModels, we will use @IntoMap annotation to map each
     * ViewModel our application uses to the ViewModel class name using @ViewModelKey annotation.
     * Instead of creating one module per ViewModel, we use the term Multi Bindings to tell Dagger how associate a
     * certain number of dependencies into a Map.
     */
}