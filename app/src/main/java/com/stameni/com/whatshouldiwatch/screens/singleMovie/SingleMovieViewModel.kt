package com.stameni.com.whatshouldiwatch.screens.singleMovie

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast.FetchSingleMovieActors
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.certification.FetchSingleMovieCertification
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.details.FetchSingleMovieDetails
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.images.FetchSingleMovieImages
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendations
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.trailer.FetchSingleMovieTrailer
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(
    private val fetchSingleMovieImages: FetchSingleMovieImages,
    private val fetchSingleMovieActors: FetchSingleMovieActors,
    private val fetchSingleMovieRecommendations: FetchSingleMovieRecommendations,
    private val fetchSingleMovieDetails: FetchSingleMovieDetails,
    private val fetchSingleMovieCertification: FetchSingleMovieCertification,
    private val fetchSingleMovieTrailer: FetchSingleMovieTrailer
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

    val fetchedCertification
        get() = fetchSingleMovieCertification.fetchedCertification

    val fetchedTrailerUrl
        get() = fetchSingleMovieTrailer.fetchedTrailerUrl

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

    fun fetchSingleMovieTrailer(movieId: Int) {
        disposables.add(
            fetchSingleMovieTrailer.getSingleMovieTrailer(movieId)
        )
    }

    fun fetchSingleMovieCertification(movieId: Int) {
        disposables.add(
            fetchSingleMovieCertification.getSingleMovieCertification(movieId)
        )
    }
}