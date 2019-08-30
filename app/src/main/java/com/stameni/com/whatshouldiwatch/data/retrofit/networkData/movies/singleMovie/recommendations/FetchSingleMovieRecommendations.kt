package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.recommendations

import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.RecommendationsMovieResults

interface FetchSingleMovieRecommendations {
    fun formatSingleMovieRecommendationsData(response: List<RecommendationsMovieResults>): ArrayList<Movie>
}