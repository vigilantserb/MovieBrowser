package com.stameni.com.whatshouldiwatch.screens.singleMovie

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast.FetchSingleMovieActors
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.details.FetchSingleMovieDetails
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.details.FetchSingleMovieDetailsImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.images.FetchSingleMovieImages
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendations
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(
    private val fetchSingleMovieImages: FetchSingleMovieImages,
    private val fetchSingleMovieActors: FetchSingleMovieActors,
    private val fetchSingleMovieRecommendations: FetchSingleMovieRecommendations,
    private val fetchSingleMovieDetails: FetchSingleMovieDetails
) : ViewModel() {

    val disposables = CompositeDisposable()

    val fetchedImages
        get() = fetchSingleMovieImages.fetchedImages

    val fetchedActors
        get() = fetchSingleMovieActors.fetchedActors

    val fetchedDetails
        get() = fetchSingleMovieDetails.movieDetails

    val fetchedRecommendations
        get() = fetchSingleMovieRecommendations.fetchedData

    fun fetchSingleMovieImages(movieId: Int) {
        disposables.add(
            fetchSingleMovieImages.getSingleMovieImages(movieId)
        )
    }

    fun fetchSingleMovieActors(movieId: Int) {
        disposables.add(
            fetchSingleMovieActors.getSingleMovieActors(movieId)
        )
    }

    fun fetchSingleMovieRecommendations(movieId: Int) {
        disposables.add(
            fetchSingleMovieRecommendations.singleMovieRecommendations(movieId)
        )
    }

    fun fetchSingleMovieDetails(movieId: Int) {
        disposables.add(
            fetchSingleMovieDetails.getSingleMovieDetails(movieId)
        )
    }
}