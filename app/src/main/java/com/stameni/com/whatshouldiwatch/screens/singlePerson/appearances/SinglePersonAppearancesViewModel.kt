package com.stameni.com.whatshouldiwatch.screens.singlePerson.appearances

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.retrofit.networkData.person.actorMovies.FetchSingleActorMoviesUseCase
import com.stameni.com.whatshouldiwatch.data.retrofit.networkData.person.directorMovies.FetchSingleDirectorMovies
import io.reactivex.disposables.CompositeDisposable

class SinglePersonAppearancesViewModel(
    private val fetchSingleActorMoviesUseCase: FetchSingleActorMoviesUseCase,
    private val fetchSingleDirectorMovies: FetchSingleDirectorMovies
) : ViewModel() {

    val fetchedActorMovies = fetchSingleActorMoviesUseCase.fetchedData

    val fetchedDirectorMovies = fetchSingleDirectorMovies.fetchedData

    val disposables = CompositeDisposable()

    fun getActorMovies(id: Int) {
        disposables.add(fetchSingleActorMoviesUseCase.fetchSingleActorMovies(id))
    }

    fun getDirectorMovies(id: Int) {
        disposables.add(fetchSingleDirectorMovies.fetchSingleDirectorMovies(id))
    }
}
