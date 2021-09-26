package com.franco.mytestapplication.domain.local.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:16 p.m.
 */
@Entity(tableName = "video_movie")
data class MovieVideo(
    @ColumnInfo(name = "video_id")
    val videoId: String,
    @ColumnInfo(name = "tmdbId")
    val tmdbid: Int,
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "site")
    val site: String,
    @ColumnInfo(name = "size")
    val size: Int,
    @ColumnInfo(name = "type")
    val type: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @Ignore
    var nowPlaying: Boolean = false
}