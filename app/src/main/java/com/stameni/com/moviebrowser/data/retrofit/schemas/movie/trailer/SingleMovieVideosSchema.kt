package com.stameni.com.moviebrowser.data.retrofit.schemas.movie.trailer


import com.google.gson.annotations.SerializedName

data class SingleMovieVideosSchema(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)