package com.stameni.com.whatshouldiwatch.data.models.movie

data class MovieDetails(
    val movieId: Int?,
    val movieTitle: String?,
    val posterPath: String?,
    val movieDescription: String?,
    val imdbId: String?,
    val tmdbRating: Double?,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: String?,
    val directorName: String?,
    val directorImageUrl: String?,
    val directorId: Int
)