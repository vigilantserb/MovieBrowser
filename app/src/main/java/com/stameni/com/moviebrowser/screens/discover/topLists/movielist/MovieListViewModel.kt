package com.stameni.com.moviebrowser.screens.discover.topLists.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchListMoviesUseCase
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(
    private val fetchListMoviesUseCase: FetchListMoviesUseCase
): ViewModel() {


    val fetchedGenres: LiveData<ArrayList<Movie>>
        get() {
            return fetchListMoviesUseCase.fetchedMovies
        }

    val fetchError: LiveData<String>
        get() {
            return fetchListMoviesUseCase.fetchError
        }

    var disposable = CompositeDisposable()

    fun getListMovies(listId: String) {
        disposable.add(fetchListMoviesUseCase.getListMovies(listId))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}