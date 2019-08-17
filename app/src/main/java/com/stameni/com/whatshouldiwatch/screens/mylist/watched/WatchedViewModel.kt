package com.stameni.com.whatshouldiwatch.screens.mylist.watched

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.room.localData.DeleteMovieUseCase
import com.stameni.com.whatshouldiwatch.data.room.localData.FetchMovieListUseCase
import com.stameni.com.whatshouldiwatch.data.room.localData.UpdateMovieDataUseCase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import io.reactivex.disposables.CompositeDisposable

class WatchedViewModel(
    private val deleteSingleMovie: DeleteMovieUseCase,
    private val fetchMovieListUseCase: FetchMovieListUseCase,
    private val updateMovieDataUseCase: UpdateMovieDataUseCase
) : ViewModel() {

    val disposable = CompositeDisposable()

    val fetchedMovies = fetchMovieListUseCase.fetchedMovies

    val successMessage = updateMovieDataUseCase.successMessage

    fun deleteSingleMovie(movie: Movie){
        deleteSingleMovie.deleteMovie(movie)
    }

    fun updateMovie(movie: Movie, listType: String) {
        updateMovieDataUseCase.updateMovieData(movie, listType)
    }

    fun fetchListMovies(listType: String) {
        disposable.add(fetchMovieListUseCase.fetchMovies(listType))
    }
}