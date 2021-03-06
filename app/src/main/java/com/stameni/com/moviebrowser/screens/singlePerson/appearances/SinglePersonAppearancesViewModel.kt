package com.stameni.com.moviebrowser.screens.singlePerson.appearances

import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies.FetchSingleActorMoviesUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.directorMovies.FetchSingleDirectorMovies
import io.reactivex.disposables.CompositeDisposable

class SinglePersonAppearancesViewModel(
    private val fetchSingleActorMoviesUseCase: FetchSingleActorMoviesUseCase,
    private val fetchSingleDirectorMovies: FetchSingleDirectorMovies
) : ViewModel() {

    val fetchedActorMovies = fetchSingleActorMoviesUseCase.fetchedData

    val fetchedDirectorMovies = fetchSingleDirectorMovies.fetchedData

    val disposables = CompositeDisposable()

    fun getActorMovies(id: Int, page: Int) {
        disposables.add(fetchSingleActorMoviesUseCase.fetchSingleActorMovies(id, page))
    }

    fun getDirectorMovies(id: Int, page: Int) {
        disposables.add(fetchSingleDirectorMovies.fetchSingleDirectorMovies(id, page))
    }
}
