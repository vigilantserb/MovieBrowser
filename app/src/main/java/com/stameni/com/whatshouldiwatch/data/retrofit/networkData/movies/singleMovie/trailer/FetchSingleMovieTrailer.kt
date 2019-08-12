package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.trailer

import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable

interface FetchSingleMovieTrailer {

    val fetchedTrailerUrl: LiveData<String>

    val fetchError: LiveData<Exception>

    fun getSingleMovieTrailer(movieId: Int): Disposable
}