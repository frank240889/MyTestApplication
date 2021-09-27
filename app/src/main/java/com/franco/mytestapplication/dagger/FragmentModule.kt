package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.presentation.map.MapFragment
import com.franco.mytestapplication.presentation.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Module to create injectors for fragments.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:31 a. m.
 */
@Module
abstract class FragmentModule {

    /**
     * Indicates this [MoviesFragment] is injectable by Dagger.
     */
    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment

    /**
     * Indicates this [MapFragment] is injectable by Dagger.
     */
    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment

}