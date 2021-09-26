package com.franco.mytestapplication.domain.remote.models.moviedetail

import com.google.gson.annotations.SerializedName

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:35 p.m.
 */
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)