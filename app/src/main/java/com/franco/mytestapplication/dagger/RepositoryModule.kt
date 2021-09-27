package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.data.datasource.MoviesRepository
import com.franco.mytestapplication.interfaces.Repository
import dagger.Binds
import dagger.Module


/**
 * Provides the repository to consume data sources.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:46 a. m.
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsMoviesRepository(moviesRepository: MoviesRepository): Repository
}