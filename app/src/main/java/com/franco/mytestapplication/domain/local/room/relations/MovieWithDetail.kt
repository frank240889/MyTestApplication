package com.franco.mytestapplication.domain.local.room.relations

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.local.room.tables.MovieCast
import com.franco.mytestapplication.domain.local.room.tables.MovieCrew
import com.franco.mytestapplication.domain.local.room.tables.MovieDetail
import com.franco.mytestapplication.domain.local.room.tables.MovieGenre
import com.franco.mytestapplication.domain.local.room.tables.MovieProductionCompany
import com.franco.mytestapplication.domain.local.room.tables.MovieSpokenLanguage
import com.franco.mytestapplication.domain.local.room.tables.MovieVideo

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:10 p.m.
 */
/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 10:10 p.m.
 */
data class MovieWithDetail (
    @Embedded
    val detail: MovieDetail,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId",
        entity = Movie::class
    )
    val movie: Movie,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    val company: List<MovieProductionCompany>,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    val genres: List<MovieGenre>,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    val cast: List<MovieCast>,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    val crew: List<MovieCrew>,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    val spokenLanguage: List<MovieSpokenLanguage>,

    @Relation(
        parentColumn = "id",
        entityColumn = "tmdbId"
    )
    var movieVideo: List<MovieVideo>,
) {
    @Ignore
    var isLocalCopy: Boolean = false
}