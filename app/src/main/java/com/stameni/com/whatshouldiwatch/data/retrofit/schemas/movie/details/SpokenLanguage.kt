package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details


import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)