package com.stameni.com.moviebrowser.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.movie.Movie
import io.reactivex.disposables.Disposable

interface FetchListMoviesUseCase {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val fetchError: LiveData<String>

    fun getListMovies(listId: String): Disposable
}