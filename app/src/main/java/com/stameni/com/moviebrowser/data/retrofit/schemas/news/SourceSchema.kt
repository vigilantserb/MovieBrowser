package com.stameni.com.moviebrowser.data.retrofit.schemas.news

import com.google.gson.annotations.SerializedName

data class SourceSchema(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)