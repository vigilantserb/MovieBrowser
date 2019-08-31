package com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.SearchItem
import io.reactivex.disposables.Disposable

interface FetchSingleActorMoviesUseCase {

    val fetchedData: LiveData<ArrayList<SearchItem>>

    val fetchError: LiveData<Exception>

    fun fetchSingleActorMovies(castId: Int, page: Int): Disposable
}