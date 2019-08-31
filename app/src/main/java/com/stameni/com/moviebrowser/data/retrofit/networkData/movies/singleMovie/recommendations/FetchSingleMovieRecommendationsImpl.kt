package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.recommendations

import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.RecommendationsMovieResults

class FetchSingleMovieRecommendationsImpl : FetchSingleMovieRecommendations {

    override fun formatSingleMovieRecommendationsData(response: List<RecommendationsMovieResults>): ArrayList<Movie> {
        val formattedData = ArrayList<Movie>()
        response.forEach {
            if (it.posterPath != null)
                formattedData.add(
                    Movie(
                        it.id,
                        it.title,
                        it.releaseDate,
                        "",
                        it.posterPath,
                        it.voteAverage
                    )
                )
        }


        return formattedData
    }
}