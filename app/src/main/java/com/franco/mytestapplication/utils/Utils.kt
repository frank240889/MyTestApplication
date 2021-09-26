package com.franco.mytestapplication.utils

import androidx.annotation.IntDef
import com.franco.mytestapplication.R
import com.franco.mytestapplication.domain.local.room.tables.Genres
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.local.room.tables.MovieCast
import com.franco.mytestapplication.domain.remote.models.MoviesResponse
import com.franco.mytestapplication.domain.remote.models.configuration.ConfigurationResponse
import com.franco.mytestapplication.interfaces.AbstractSharedPreferences
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
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

    fun createUrlImage(configuration: ConfigurationResponse, imagePath: String?, @Quality quality: Int = BEST_QUALITY): String? {

        if (imagePath == null)
            return null

        val size = selectPosterSize(configuration, quality)
        return if (size == null)
            null
        else {
            val baseurl = resolveBaseUrlImage(configuration, size)
            baseurl.plus(imagePath)
        }
    }

    fun obtainConfiguration(sharedPreferencesManager: AbstractSharedPreferences): ConfigurationResponse? {
        val gson = Gson()
        val conf = sharedPreferencesManager.getString("configuration")
        return if (conf != null)
            gson.fromJson(conf, ConfigurationResponse::class.java)
        else
            null

    }

    fun createMovies(response: MoviesResponse, genres: List<Genres>, baseurl: String): List<Movie> {
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
                it.genreIds.map { genreId ->
                    Genres(
                        genreId,
                        genres.find { genreAndName ->
                            genreId == genreAndName.genreId
                        }!!.name)
                }.joinToString(),
                favorite = false,
                pendingToWatch = false
            )
        }
    }

    fun unixTimeFromString(date: String) = run {
        try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)!!.time
        } catch (e: ParseException) {
            0
        }
    }


    fun createCast(movieCast: List<MovieCast>, configuration: ConfigurationResponse) =
        movieCast.map {
            CastAdapter.Cast(
                it.name,
                it.character,
                createUrlImage(configuration, it.profilePath, AUTOMATIC_QUALITY)
            )
        }

    fun createCrew(movieCast: List<MovieCrew>, configuration: ConfigurationResponse) =
        movieCast.map {
            CrewAdapter.Crew(
                it.name,
                it.job,
                createUrlImage(configuration, it.profilePath, AUTOMATIC_QUALITY)
            )
        }


    fun createCompanies(movieProductionCompany: List<MovieProductionCompany>) =
        if (movieProductionCompany.isEmpty())
            null
        else
            movieProductionCompany.joinToString { company -> company.name }

    fun createGenres(genres: List<MovieGenre>) =
        if (genres.isNullOrEmpty())
            null
        else
            genres.joinToString { genre -> genre.name}

    fun resolveMessageErrorYoutube(
        error: PlayerConstants.PlayerError,
        resourceManager: ResourceManager): String {
        return when(error) {
            PlayerConstants.PlayerError.HTML_5_PLAYER,
            PlayerConstants.PlayerError.INVALID_PARAMETER_IN_REQUEST ->
                resourceManager.getString(R.string.internal_error)
            PlayerConstants.PlayerError.VIDEO_NOT_FOUND -> resourceManager.getString(R.string.video_not_found)
            PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER -> resourceManager.getString(R.string.video_no_playable)
            else -> resourceManager.getString(R.string.default_error_message)
        }
    }
}

const val AUTOMATIC_QUALITY = -1
const val BEST_QUALITY = -2
const val LOWER_QUALITY = -3

@IntDef(BEST_QUALITY, LOWER_QUALITY, AUTOMATIC_QUALITY)
@Retention(AnnotationRetention.SOURCE)
annotation class Quality
