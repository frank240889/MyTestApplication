package com.franco.mytestapplication.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.franco.mytestapplication.domain.local.room.tables.Movie

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:16 p.m.
 */
@ExperimentalPagingApi
class MoviesRemoteMediator constructor(
    private val loadingTask: suspend (LoadType, PagingState<Int, Movie>) -> MediatorResult
): RemoteMediator<Int, Movie>() {

    override suspend fun initialize() = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>) =
        loadingTask.invoke(loadType, state)
}