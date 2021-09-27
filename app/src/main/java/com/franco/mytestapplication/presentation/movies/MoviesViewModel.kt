package com.franco.mytestapplication.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.franco.mytestapplication.domain.local.usecase.FetchMoviesUseCase
import javax.inject.Inject

/**
 * View model for MoviesFragment.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 3:25 p.m.
 */
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {

    @ExperimentalPagingApi
    fun fetchMovies() = fetchMoviesUseCase.execute()

}