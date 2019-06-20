package com.stameni.com.whatshouldiwatch.data.networkData.lists

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Movie
import io.reactivex.disposables.Disposable
import java.lang.Exception

interface FetchListMoviesUseCase {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val fetchError: LiveData<Exception>

    fun getListMovies(listId: String): Disposable
}