package com.franco.mytestapplication.domain.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:13 p.m.
 */
@Entity(
    tableName = "movie_crew",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["tmdbId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieCrew(
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Int,
    @ColumnInfo(name = "job")
    val job: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "profile_path")
    val profilePath: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}