package com.franco.mytestapplication.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.franco.mytestapplication.data.remote.TheMovieDbApi
import com.franco.mytestapplication.data.remote.TheMovieDbApi.Companion.PAGE_POPULAR
import com.franco.mytestapplication.domain.local.room.db.MoviesDatabase
import com.franco.mytestapplication.domain.local.room.tables.Genres
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse
import com.franco.mytestapplication.interfaces.AbstractSharedPreferences
import com.franco.mytestapplication.interfaces.Repository
import com.google.gson.Gson
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 7:15 p.m.
 */
class MoviesRepository(
    private val sharedPreferences: AbstractSharedPreferences,
    private val api: TheMovieDbApi,
    private val db: MoviesDatabase
): Repository {

    private val gson = Gson()
    private var configuration: ConfigurationResponse? = null

    private val genres: MutableList<Genres> by lazy {
        ArrayList()
    }

    @ExperimentalPagingApi
    override fun fetchMovies(): Flow<PagingData<Movie>> {
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
            pagingSourceFactory = { db.myMoviesDao().fetchTopRatedMovies() }
        ).flow
    }

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
            .addQueryParam(TheMovieDbApi.PAGE, page.toString())
            .build()

        try {

            if (configuration == null) {
                fetchConfiguration()
            }
            if (genres.isEmpty()) {
                fetchGenres(api, db, genres)
            }

            val response = apiCall.invoke(queryParams.getParams())
            val endOfPagination = response.page == response.totalPages
            var size: String? = null
            Utils.selectPosterSize(configuration!!, AUTOMATIC_QUALITY)?.let {
                size = it
            }
            val baseurl = Utils.resolveBaseUrlImage(configuration!!, size)
            db.withTransaction {
                db.myMoviesDao().insert(Utils.createMovies(response, genres, baseurl))
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
     * Fetch configuration and saves into shared preferences.
     */
    private suspend fun fetchConfiguration() = with(api.fetchConfiguration()) {
        configuration = this
        sharedPreferencesManager.saveString("configuration", gson.toJson(this))
    }

    companion object {
        suspend fun fetchGenres(
            api: TheMovieDbApi,
            db: MyMoviesDatabase,
            emptyGenresList: MutableList<Genres>
        ) {
            api.fetchGenres().apply {
                db.withTransaction {
                    db.myMoviesDao().insertGenres(genres.map {
                        Genres(it.id, it.name)
                    }.apply {
                        emptyGenresList.addAll(this)
                    })
                }
            }
        }
    }
}