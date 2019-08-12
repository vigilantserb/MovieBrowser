package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.certification


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("iso_3166_1")
    val country: String,
    @SerializedName("release_dates")
    val certificationList: List<Certification>
)