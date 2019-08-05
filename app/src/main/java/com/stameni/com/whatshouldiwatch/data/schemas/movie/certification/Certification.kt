package com.stameni.com.whatshouldiwatch.data.schemas.movie.certification


import com.google.gson.annotations.SerializedName

data class Certification(
    @SerializedName("certification")
    val certification: String
)