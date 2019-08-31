package com.stameni.com.moviebrowser.data.retrofit.schemas.person


import com.google.gson.annotations.SerializedName

data class ActorSchema(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String = ""
)