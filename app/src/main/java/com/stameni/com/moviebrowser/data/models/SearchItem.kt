package com.stameni.com.moviebrowser.data.models

data class SearchItem(
    val title: String?,
    val url: String?,
    val type: String?,
    val year: String,
    val id: Int,
    val totalResults: Int = 0
)
