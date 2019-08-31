package com.stameni.com.moviebrowser.screens.mylist.toWatch

import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.room.localData.DeleteMovieUseCase
import com.stameni.com.moviebrowser.data.room.localData.FetchMovieListUseCase
import com.stameni.com.moviebrowser.data.room.localData.UpdateMovieDataUseCase
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import io.reactivex.disposables.CompositeDisposable

class ToWatchViewModel (
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