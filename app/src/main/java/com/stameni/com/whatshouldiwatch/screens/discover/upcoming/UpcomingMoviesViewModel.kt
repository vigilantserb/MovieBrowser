package com.stameni.com.whatshouldiwatch.screens.discover.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.models.Movie
import com.stameni.com.whatshouldiwatch.data.networkData.FetchUpcomingMovies
import io.reactivex.disposables.CompositeDisposable

class UpcomingMoviesViewModel(private val fetchUpcomingMovies: FetchUpcomingMovies) : ViewModel() {

    val fetchedMovies: LiveData<ArrayList<Movie>>
        get() {
            return fetchUpcomingMovies.fetchedMovies
        }

    val fetchError: LiveData<Exception>
        get() {
            return fetchUpcomingMovies.fetchError
        }

    var disposable = CompositeDisposable()

    fun getUpcomingMovies(page: Int) {
        disposable.add(fetchUpcomingMovies.getUpcomingMovies(page))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}
