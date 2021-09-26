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
    tableName = "movie_production_company",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["tmdbId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieProductionCompany (
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Int,
    @ColumnInfo(name = "logo_path")
    val logoPath: String,
    @ColumnInfo(name = "name")
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}