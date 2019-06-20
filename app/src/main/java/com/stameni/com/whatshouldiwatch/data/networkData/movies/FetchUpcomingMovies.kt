package com.stameni.com.whatshouldiwatch.data.networkData.movies

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Movie
import io.reactivex.disposables.Disposable

interface FetchUpcomingMovies {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val totalPages: LiveData<Int>

    val fetchError: LiveData<Exception>

    fun getUpcomingMovies(page: Int): Disposable
}