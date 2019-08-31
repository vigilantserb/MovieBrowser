package com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class ReleaseDate(
    @SerializedName("certification")
    val certification: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("type")
    val type: Int
)