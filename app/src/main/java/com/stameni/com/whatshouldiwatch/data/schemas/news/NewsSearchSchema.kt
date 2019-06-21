package com.stameni.com.whatshouldiwatch.data.schemas.news

import com.google.gson.annotations.SerializedName

data class NewsSearchSchema(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<ArticleSchema>
)