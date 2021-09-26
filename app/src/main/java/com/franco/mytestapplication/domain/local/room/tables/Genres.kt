package com.franco.mytestapplication.domain.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:11 p.m.
 */
@Entity(tableName = "genres")
data class Genres(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "name")
    val name: String,
)