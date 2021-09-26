package com.franco.mytestapplication.domain.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:15 p.m.
 */
@Entity(
    tableName = "movie_genre",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["tmdbId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieGenre(
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "name")
    val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}