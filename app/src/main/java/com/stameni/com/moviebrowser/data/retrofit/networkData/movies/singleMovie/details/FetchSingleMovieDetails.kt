package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.details

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.movie.Actor
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.models.movie.MovieDetails
import com.stameni.com.moviebrowser.data.models.movie.MovieImage
import io.reactivex.disposables.Disposable

interface FetchSingleMovieDetails {

    val movieDetails: LiveData<MovieDetails>

    val fetchError: LiveData<String>

    fun getSingleMovieDetails(movieId: Int): Disposable
    val fetchedImages: LiveData<ArrayList<MovieImage>>
    val fetchedActors: LiveData<ArrayList<Actor>>
    val fetchedRecommendations: LiveData<ArrayList<Movie>>
    val fetchedTrailerUrl: LiveData<String>
    val fetchedCertification: LiveData<String>
}