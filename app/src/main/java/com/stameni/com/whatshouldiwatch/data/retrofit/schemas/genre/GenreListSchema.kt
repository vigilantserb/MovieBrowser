package com.stameni.com.whatshouldiwatch.data.retrofit.schemas.genre


import com.google.gson.annotations.SerializedName
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.BaseSchema

data class GenreListSchema(
    @SerializedName("genres")
    val genres: List<GenreSchema>
): BaseSchema()