package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.images

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieImage
import io.reactivex.disposables.Disposable

interface FetchSingleMovieImages {

    val fetchedImages: LiveData<ArrayList<MovieImage>>

    val fetchError: LiveData<Exception>

    fun getSingleMovieImages(movieId: Int): Disposable
}