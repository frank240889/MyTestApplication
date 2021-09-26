package com.franco.mytestapplication.interfaces

import androidx.paging.PagingData
import com.franco.mytestapplication.domain.local.room.tables.Movie
import kotlinx.coroutines.flow.Flow

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:12 p.m.
 */
interface Repository {
    fun fetchMovies(): Flow<PagingData<Movie>>
}