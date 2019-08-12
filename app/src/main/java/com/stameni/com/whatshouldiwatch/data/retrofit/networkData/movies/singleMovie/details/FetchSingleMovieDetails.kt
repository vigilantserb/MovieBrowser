package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.details

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import io.reactivex.disposables.Disposable

interface FetchSingleMovieDetails {

    val movieDetails: LiveData<MovieDetails>

    val fetchError: LiveData<Exception>

    fun getSingleMovieDetails(movieId: Int): Disposable
}