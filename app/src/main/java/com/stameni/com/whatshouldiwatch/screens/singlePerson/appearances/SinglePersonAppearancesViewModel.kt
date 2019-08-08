package com.stameni.com.whatshouldiwatch.screens.singlePerson.appearances

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.person.actorMovies.FetchSingleActorMoviesUseCase
import io.reactivex.disposables.CompositeDisposable

class SinglePersonAppearancesViewModel(
    private val fetchSingleActorMoviesUseCase: FetchSingleActorMoviesUseCase
) : ViewModel() {

    val fetchedMovies = fetchSingleActorMoviesUseCase.fetchedData

    val numberOfMovies = fetchSingleActorMoviesUseCase.personMovieNumber

    val disposables = CompositeDisposable()

    fun getActorMovies(id: Int) {
        disposables.add(fetchSingleActorMoviesUseCase.fetchSingleActorMovies(id))
    }
}
