package com.franco.mytestapplication.domain.remote.models.genre

import com.google.gson.annotations.SerializedName

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:34 p.m.
 */
data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)