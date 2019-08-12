package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.certification


import com.google.gson.annotations.SerializedName

data class CertificationResults(
    @SerializedName("results")
    val results: List<Result>
)