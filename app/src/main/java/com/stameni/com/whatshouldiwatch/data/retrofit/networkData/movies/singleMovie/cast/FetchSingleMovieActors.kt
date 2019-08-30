package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.cast

import com.stameni.com.whatshouldiwatch.data.models.movie.Actor
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.Cast

interface FetchSingleMovieActors {
    fun formatSingleMovieActorsResponse(response: List<Cast>): ArrayList<Actor>
}