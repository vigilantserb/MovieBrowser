package com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class ReleaseDatesResults(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("release_dates")
    val releaseDates: List<ReleaseDate>?
)