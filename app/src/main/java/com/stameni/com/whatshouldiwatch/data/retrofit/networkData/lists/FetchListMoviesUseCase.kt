package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import io.reactivex.disposables.Disposable

interface FetchListMoviesUseCase {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val fetchError: LiveData<Exception>

    fun getListMovies(listId: String): Disposable
}