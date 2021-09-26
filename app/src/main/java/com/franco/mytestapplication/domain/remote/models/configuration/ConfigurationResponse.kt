package com.franco.mytestapplication.domain.remote.models.configuration

import com.franco.mytestapplication.domain.remote.models.movies.Images
import com.google.gson.annotations.SerializedName

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:34 p.m.
 */
data class ConfigurationResponse(
    @SerializedName("change_keys")
    val changeKeys: List<String>,
    @SerializedName("images")
    val images: Images
)