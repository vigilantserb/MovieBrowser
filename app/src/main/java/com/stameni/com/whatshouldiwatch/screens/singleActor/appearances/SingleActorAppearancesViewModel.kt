package com.stameni.com.whatshouldiwatch.screens.singleActor.appearances

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.actor.FetchSingleActorMoviesUseCase
import io.reactivex.disposables.CompositeDisposable

class SingleActorAppearancesViewModel(
    private val fetchSingleActorMoviesUseCase: FetchSingleActorMoviesUseCase
) : ViewModel() {

    val fetchedMovies = fetchSingleActorMoviesUseCase.fetchedData

    val disposables = CompositeDisposable()

    fun getActorMovies(id: Int) {
        disposables.add(fetchSingleActorMoviesUseCase.fetchSingleActorMovies(id))
    }
}
