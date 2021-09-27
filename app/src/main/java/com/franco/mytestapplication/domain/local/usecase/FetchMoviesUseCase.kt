package com.franco.mytestapplication.domain.local.usecase

import androidx.paging.PagingData
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.interfaces.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / email@domain.com
 * Created 26/09/2021 at 02:40 a. m.
 */
class FetchMoviesUseCase @Inject constructor(
    private val repository: Repository
): UseCase<Flow<PagingData<Movie>>>() {

    override fun execute(): Flow<PagingData<Movie>> = repository.fetchMovies()

}