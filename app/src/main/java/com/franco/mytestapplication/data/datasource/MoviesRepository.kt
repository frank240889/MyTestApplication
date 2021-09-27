package com.franco.mytestapplication.data.datasource

import androidx.paging.*
import androidx.room.withTransaction
import com.franco.mytestapplication.data.remote.CONFIGURATION
import com.franco.mytestapplication.data.remote.PAGE
import com.franco.mytestapplication.data.remote.PAGE_POPULAR
import com.franco.mytestapplication.data.remote.TheMovieDbApi
import com.franco.mytestapplication.domain.local.room.db.MoviesDatabase
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse
import com.franco.mytestapplication.interfaces.AbstractSharedPreferences
import com.franco.mytestapplication.interfaces.Repository
import com.franco.mytestapplication.utils.AUTOMATIC_QUALITY
import com.franco.mytestapplication.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * An implementation of repository.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:15 p.m.
 */

class MoviesRepository @Inject constructor(
    private val sharedPreferences: AbstractSharedPreferences,
    private val api: TheMovieDbApi,
    private val db: MoviesDatabase
): Repository {

    private val gson = Gson()
    private var configuration: ConfigurationResponse? = null

    @ExperimentalPagingApi
    override fun fetchMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 4,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator { loadType, pagingState ->
                loadMoviesTask(loadType, pagingState, PAGE_POPULAR) {
                    api.fetchPopularMovies(it)
                }
            },
            pagingSourceFactory = { db.myMoviesDao().fetchMovies() }
        ).flow


    @ExperimentalPagingApi
    private suspend fun loadMoviesTask(loadType: LoadType,
                                       state: PagingState<Int, Movie>,
                                       pageQuery: String,
                                       apiCall: suspend (Map<String, String>) -> MoviesResponse
    ): RemoteMediator.MediatorResult {

        val page = when(loadType) {
            LoadType.APPEND -> getLastPage(pageQuery)
            LoadType.PREPEND -> return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
            LoadType.REFRESH -> initialPage()
        }

        val queryParams = TheMovieDbApi.QueryParams
            .Builder()
            .addQueryParam(PAGE, page.toString())
            .build()

        try {

            if (configuration == null) {
                fetchConfiguration()
            }

            val response = apiCall.invoke(queryParams.getParams())
            val endOfPagination = response.page == response.totalPages
            var size: String? = null
            Utils.selectPosterSize(configuration!!, AUTOMATIC_QUALITY)?.let {
                size = it
            }
            val baseurl = Utils.resolveBaseUrlImage(configuration!!, size)
            db.withTransaction {
                db.myMoviesDao().insert(Utils.createMovies(response, baseurl))
                sharedPreferences.saveInt(pageQuery, response.page + 1)
            }
            return RemoteMediator.MediatorResult.Success(endOfPaginationReached = endOfPagination)
        }
        catch (exception: IOException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
        catch (exception: HttpException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
        catch (exception: UnknownHostException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
        catch (exception: TimeoutException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
    }

    private fun getLastPage(queryString: String) =
        if (sharedPreferences.getInt(queryString) == -1)
            initialPage()
        else
            (sharedPreferences.getInt(queryString))

    private fun initialPage() = 1

    /**
     * Fetch configuration and saves into shared preferences. Configuration is necessary to be able
     * to fetch a valid poster size.
     */
    private suspend fun fetchConfiguration() = with(api.fetchConfiguration()) {
        configuration = this
        sharedPreferences.saveString(CONFIGURATION, gson.toJson(this))
    }
}