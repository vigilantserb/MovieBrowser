package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.recommendations

import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.RecommendationsMovieResults

interface FetchSingleMovieRecommendations {
    fun formatSingleMovieRecommendationsData(response: List<RecommendationsMovieResults>): ArrayList<Movie>
}