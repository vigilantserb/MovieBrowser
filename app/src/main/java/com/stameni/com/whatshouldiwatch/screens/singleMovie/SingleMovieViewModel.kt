package com.stameni.com.whatshouldiwatch.screens.singleMovie

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.details.FetchSingleMovieDetails
import com.stameni.com.whatshouldiwatch.data.room.localData.SaveMovieToDatabase
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(
    private val fetchSingleMovieDetails: FetchSingleMovieDetails,
    private val saveMovieToDatabase: SaveMovieToDatabase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val fetchedImages
        get() = fetchSingleMovieDetails.fetchedImages

    val fetchedActors
        get() = fetchSingleMovieDetails.fetchedActors

    val fetchedDetails
        get() = fetchSingleMovieDetails.movieDetails

    val fetchedRecommendations
        get() = fetchSingleMovieDetails.fetchedRecommendations

    val fetchedCertification
        get() = fetchSingleMovieDetails.fetchedCertification

    val fetchedTrailerUrl
        get() = fetchSingleMovieDetails.fetchedTrailerUrl

    val saveMovieMessage= saveMovieToDatabase.successMessage

    fun saveMovieToDatabase(movieDetails: MovieDetails, listType: String){
        disposables.add(
            saveMovieToDatabase.addMovieToDatabase(movieDetails, listType)
        )
    }

    fun fetchSingleMovieDetails(movieId: Int) {
        disposables.add(
            fetchSingleMovieDetails.getSingleMovieDetails(movieId)
        )
    }
}