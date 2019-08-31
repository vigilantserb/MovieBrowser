package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.cast

import com.stameni.com.moviebrowser.data.models.movie.Actor
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.Cast

class FetchSingleMovieActorsImpl : FetchSingleMovieActors {

    override fun formatSingleMovieActorsResponse(response: List<Cast>): ArrayList<Actor> {
        val formattedData = ArrayList<Actor>()
        response.forEach {
            formattedData.add(
                Actor(
                    it.id,
                    it.name,
                    it.character,
                    it.profilePath
                )
            )
        }
        return formattedData
    }
}