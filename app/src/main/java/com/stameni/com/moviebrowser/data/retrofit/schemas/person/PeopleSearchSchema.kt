package com.stameni.com.moviebrowser.data.retrofit.schemas.person


import com.google.gson.annotations.SerializedName

data class PeopleSearchSchema(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ActorSchema>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)