package com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.FetchMoviesByGenreUseCase
import io.reactivex.disposables.CompositeDisposable

class MovieGridViewModel(
    private val fetchMoviesByGenre: FetchMoviesByGenreUseCase
): ViewModel() {

    val fetchedMovies: LiveData<ArrayList<Movie>>
        get() {
            return fetchMoviesByGenre.fetchedMovies
        }

    val totalPages: LiveData<Int>
        get() {
            return fetchMoviesByGenre.totalPages
        }

    val fetchError: LiveData<String>
        get() {
            return fetchMoviesByGenre.fetchError
        }

    var disposable = CompositeDisposable()

    fun getListMovies(genreId: Int, page: Int) {
        disposable.add(fetchMoviesByGenre.getMoviesWithGenre(genreId, page))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}