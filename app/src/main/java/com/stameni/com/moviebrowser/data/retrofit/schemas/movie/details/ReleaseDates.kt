package com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class ReleaseDates(
    @SerializedName("results")
    val results: List<ReleaseDatesResults>?
)