package com.franco.mytestapplication.utils

import androidx.annotation.IntDef
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse

/**
 * Object with utility methods.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 10:54 p.m.
 */
object Utils {

    /**
     * Returns the base url plus the size for image loading.
     */
    fun resolveBaseUrlImage(configuration: ConfigurationResponse, size: String?): String {
        val baseUrl = configuration.images.baseUrl
        return if (size == null) "" else baseUrl.plus(size)
    }

    /**
     * Select a image based on the position [selection], if index is greater than size or -1 is
     * passed, an automatic selection will be done.
     *
     * @return null if there are no images sizes, a string otherwise
     */
    fun selectPosterSize(configuration: ConfigurationResponse,
                         @Quality selection: Int = AUTOMATIC_QUALITY): String? {
        return when {
            configuration.images.posterSizes.isEmpty() -> {
                null
            }
            configuration.images.posterSizes.size == 1 || selection == LOWER_QUALITY -> {
                configuration.images.posterSizes[0]
            }
            selection == BEST_QUALITY -> {
                if (configuration.images.posterSizes[configuration.images.posterSizes.size - 1].contains("original")) {
                    configuration.images.posterSizes[configuration.images.posterSizes.size - 2]
                }
                else {
                    configuration.images.posterSizes[configuration.images.posterSizes.size - 1]
                }
            }
            selection >= 0 && selection < configuration.images.posterSizes.size -> {
                configuration.images.posterSizes[selection]
            }
            selection == AUTOMATIC_QUALITY -> {
                when {
                    configuration.images.posterSizes.size > 7 ||
                            configuration.images.posterSizes.size > 6 ||
                            configuration.images.posterSizes.size > 5 ->
                        configuration.images.posterSizes[configuration.images.posterSizes.size - 3]
                    configuration.images.posterSizes.size > 4 ->
                        configuration.images.posterSizes[configuration.images.posterSizes.size - 2]
                    configuration.images.posterSizes.size > 3 ->
                        configuration.images.posterSizes[configuration.images.posterSizes.size - 2]
                    configuration.images.posterSizes.size > 2 ->
                        configuration.images.posterSizes[configuration.images.posterSizes.size - 1]
                    configuration.images.posterSizes.size == 1 ->
                        configuration.images.posterSizes[0]

                    else -> configuration.images.posterSizes[configuration.images.posterSizes.size - 4]
                }
            }
            else -> null
        }
    }


    fun createMovies(response: MoviesResponse, baseurl: String): List<Movie> {
        return response.results.map {
            Movie(
                it.id,
                it.backdropPath ?: "",
                it.overview,
                it.posterPath ?: "",
                it.title,
                it.originalTitle,
                it.posterPath?.run {
                    baseurl.plus(this)
                } ?: "",
                it.popularity.toFloat(),
                it.voteAverage.toFloat(),
            )
        }
    }

}

const val AUTOMATIC_QUALITY = -1
const val BEST_QUALITY = -2
const val LOWER_QUALITY = -3

@IntDef(BEST_QUALITY, LOWER_QUALITY, AUTOMATIC_QUALITY)
@Retention(AnnotationRetention.SOURCE)
annotation class Quality