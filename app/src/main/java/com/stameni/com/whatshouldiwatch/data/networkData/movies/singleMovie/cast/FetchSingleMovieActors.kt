package com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.Actor
import io.reactivex.disposables.Disposable

interface FetchSingleMovieActors {

    val fetchedActors: LiveData<ArrayList<Actor>>

    val fetchError: LiveData<Exception>

    fun getSingleMovieActors(movieId: Int): Disposable
}