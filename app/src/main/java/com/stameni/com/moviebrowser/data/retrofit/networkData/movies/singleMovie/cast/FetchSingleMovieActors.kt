package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.cast

import com.stameni.com.moviebrowser.data.models.movie.Actor
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.Cast

interface FetchSingleMovieActors {
    fun formatSingleMovieActorsResponse(response: List<Cast>): ArrayList<Actor>
}