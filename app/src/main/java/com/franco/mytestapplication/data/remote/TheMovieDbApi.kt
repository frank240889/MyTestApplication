package com.franco.mytestapplication.data.remote

import android.util.ArrayMap
import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 7:24 p.m.
 */

const val PAGE = "page"
const val PAGE_POPULAR = "page_popular"
const val CONFIGURATION = "configuration"
interface TheMovieDbApi {

    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(@QueryMap queryParams: Map<String, String>): MoviesResponse

    @GET("/3/configuration")
    suspend fun fetchConfiguration(): ConfigurationResponse

    /**
     * Helper class to construct a map that contains the query params for Api calls.
     */
    class QueryParams private constructor(data: MutableMap<String, String>) {
        private val params = ArrayMap<String, String>()

        init {
            params.putAll(data)
        }

        fun getParams() = params

        /**
         * Builder class to help to construct the map.
         */
        class Builder {
            private val map = ArrayMap<String, String>()

            fun addQueryParam(key: String, value: String) = run {
                map[key] = value
                this
            }

            fun build() = QueryParams(map)
        }
    }

}