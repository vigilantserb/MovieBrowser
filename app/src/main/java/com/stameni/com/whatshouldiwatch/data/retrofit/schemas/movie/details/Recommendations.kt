package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class Recommendations(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<RecommendationsMovieResults>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)