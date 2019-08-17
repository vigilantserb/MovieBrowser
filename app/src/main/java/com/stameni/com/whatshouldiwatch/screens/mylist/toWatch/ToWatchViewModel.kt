package com.stameni.com.whatshouldiwatch.screens.mylist.toWatch

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.room.localData.DeleteMovieUseCase
import com.stameni.com.whatshouldiwatch.data.room.localData.FetchMovieListUseCase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import io.reactivex.disposables.CompositeDisposable

class ToWatchViewModel (
    private val deleteSingleMovie: DeleteMovieUseCase,
    private val fetchMovieListUseCase: FetchMovieListUseCase
) : ViewModel() {

    val disposable = CompositeDisposable()

    val fetchedMovies = fetchMovieListUseCase.fetchedMovies

    fun deleteSingleMovie(movie: Movie){
        deleteSingleMovie.deleteMovie(movie)
    }

    fun fetchListMovies(listType: String) {
        disposable.add(fetchMovieListUseCase.fetchMovies(listType))
    }
}