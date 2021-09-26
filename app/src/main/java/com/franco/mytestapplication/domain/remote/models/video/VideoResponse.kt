package com.franco.mytestapplication.domain.remote.models.video

import com.google.gson.annotations.SerializedName

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:38 p.m.
 */
data class VideoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)