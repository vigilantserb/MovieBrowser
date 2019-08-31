package com.stameni.com.moviebrowser.data.models.movie

data class Movie (
    val movieId: Int = 0,
    val movieTitle: String,
    val movieYear: String = "",
    val movieGenres: String = "",
    val moviePosterUrl: String = "",
    val movieRating: Double = 0.0
)
