package com.stameni.com.whatshouldiwatch.data.networkData

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Movie
import io.reactivex.disposables.Disposable

interface FetchNowPlayingMovies {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val totalPages: LiveData<Int>

    val fetchError: LiveData<Exception>

    fun getNowPlayingMovies(page: Int): Disposable
}