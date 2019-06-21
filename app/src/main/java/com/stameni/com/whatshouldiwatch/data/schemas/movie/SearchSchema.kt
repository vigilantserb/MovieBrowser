package com.stameni.com.whatshouldiwatch.data.schemas.movie


import com.google.gson.annotations.SerializedName
import com.stameni.com.whatshouldiwatch.data.schemas.movie.MovieSearchSchema

data class SearchSchema(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieSearchSchema>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)