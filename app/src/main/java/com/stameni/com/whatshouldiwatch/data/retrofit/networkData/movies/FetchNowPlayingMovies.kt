package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import io.reactivex.disposables.Disposable

interface FetchNowPlayingMovies {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val totalPages: LiveData<Int>

    val fetchError: LiveData<String>

    fun getNowPlayingMovies(page: Int): Disposable
}