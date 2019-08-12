package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.cast


import com.google.gson.annotations.SerializedName

data class SingleMovieCastSchema(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("id")
    val id: Int
)