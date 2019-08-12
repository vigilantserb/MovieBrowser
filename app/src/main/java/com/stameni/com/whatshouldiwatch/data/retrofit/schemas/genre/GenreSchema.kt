package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.genre


import com.google.gson.annotations.SerializedName

data class GenreSchema(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)