package com.stameni.com.whatshouldiwatch.data.schemas.movie.trailer


import com.google.gson.annotations.SerializedName

data class SingleMovieVideosSchema(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)