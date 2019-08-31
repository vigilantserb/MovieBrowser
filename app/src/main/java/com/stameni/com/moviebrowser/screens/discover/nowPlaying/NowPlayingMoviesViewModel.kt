package com.stameni.com.moviebrowser.screens.discover.nowPlaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.FetchNowPlayingMovies
import io.reactivex.disposables.CompositeDisposable

class NowPlayingMoviesViewModel(
    private val fetchNowPlayingMovies: FetchNowPlayingMovies
) : ViewModel() {


    val fetchedMovies: LiveData<ArrayList<Movie>>
        get() {
            return fetchNowPlayingMovies.fetchedMovies
        }

    val totalPages: LiveData<Int>
        get() {
            return fetchNowPlayingMovies.totalPages
        }

    val fetchError: LiveData<String>
        get() {
            return fetchNowPlayingMovies.fetchError
        }

    var disposable = CompositeDisposable()

    fun getNowPlayingMovies(page: Int) {
        disposable.add(fetchNowPlayingMovies.getNowPlayingMovies(page))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}
