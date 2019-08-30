package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop>,
    @SerializedName("posters")
    val posters: List<Poster>
)