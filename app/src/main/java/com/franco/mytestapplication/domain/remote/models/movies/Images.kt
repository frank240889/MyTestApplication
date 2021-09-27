package com.franco.mytestapplication.domain.remote.models.movies

import com.google.gson.annotations.SerializedName

/**
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 10:31 p.m.
 */
data class Images(
    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerializedName("base_url")
    val baseUrl: String,
    @SerializedName("logo_sizes")
    val logoSizes: List<String>,
    @SerializedName("poster_sizes")
    val posterSizes: List<String>,
    @SerializedName("profile_sizes")
    val profileSizes: List<String>,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String,
    @SerializedName("still_sizes")
    val stillSizes: List<String>
)