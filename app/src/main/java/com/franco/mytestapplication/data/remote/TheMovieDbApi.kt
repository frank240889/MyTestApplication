package com.franco.mytestapplication.data.remote

import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse
import com.franco.mytestapplication.domain.remote.models.genre.GenreResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:24 p.m.
 */
interface TheMovieDbApi {

    companion object {
        const val PAGE = "page"
        const val QUERY = "query"
        const val PAGE_POPULAR = "page_popular"
        const val CONFIGURATION = "configuration"
    }

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@QueryMap queryParams: Map<String, String>): MoviesResponse

    @GET("/3/search/movie")
    suspend fun search(@QueryMap queryParams: Map<String, String>): MoviesResponse

    @GET("/3/configuration")
    suspend fun fetchConfiguration(): ConfigurationResponse

    @GET("/3/genre/movie/list")
    suspend fun fetchGenres(): GenreResponse

}