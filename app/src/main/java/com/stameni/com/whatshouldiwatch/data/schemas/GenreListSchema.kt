package com.stameni.com.whatshouldiwatch.data.schemas


import com.google.gson.annotations.SerializedName

data class GenreListSchema(
    @SerializedName("genres")
    val genres: List<GenreSchema>
)