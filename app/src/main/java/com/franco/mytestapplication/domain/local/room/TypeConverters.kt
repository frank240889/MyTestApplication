package com.franco.mytestapplication.domain.local.room

import androidx.room.TypeConverter
import com.franco.mytestapplication.domain.remote.models.cast.CastResponse
import com.franco.mytestapplication.domain.remote.models.moviedetail.DetailMovieResponse
import com.franco.mytestapplication.domain.remote.models.video.VideoResponse
import com.google.gson.Gson

/**
 * Helper object with type converters.
 *
 * In context of Room, some data cannot be stored as is, that is to say,for example a response model from
 * web service can't be stored as such object, so it must be converted to a data type that SQLITE
 * can understand. Here they come into the action the TypeConverters, once they are declared in
 * [],
 * Room can use them to make the appropriate conversion to write appropriate data tyé and then read
 * from database data and convert it into the original object.
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:23 p.m.
 */
object TypeConverters {
    @TypeConverter
    fun detailResponseFromString(data: String): DetailMovieResponse = Gson()
        .fromJson(data, DetailMovieResponse::class.java)

    @TypeConverter
    fun detailResponseToString(data: DetailMovieResponse): String = Gson().toJson(data)

    @TypeConverter
    fun videoResponseFromString(data: String): VideoResponse =
        Gson().fromJson(data, VideoResponse::class.java)

    @TypeConverter
    fun videoResponseToString(data: VideoResponse): String = Gson().toJson(data)

    @TypeConverter
    fun castResponseFromString(data: String): CastResponse =
        Gson().fromJson(data, CastResponse::class.java)

    @TypeConverter
    fun castResponseToString(data: CastResponse): String = Gson().toJson(data)

    @TypeConverter
    fun listToString(data: List<*>): String = data.joinToString()
}