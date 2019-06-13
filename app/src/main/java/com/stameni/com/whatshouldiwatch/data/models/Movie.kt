package com.stameni.com.whatshouldiwatch.data.models

data class Movie (
    val movieTitle: String,
    val movieYear: String = "",
    val movieGenres: String = "",
    val moviePosterUrl: String,
    val movieRating: Double = 0.0
)
