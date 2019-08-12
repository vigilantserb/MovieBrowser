package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.images


import com.google.gson.annotations.SerializedName

data class SingleMovieImagesSchema(
    @SerializedName("backdrops")
    val backdrops: List<Backdrop>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("posters")
    val posters: List<Poster>
)