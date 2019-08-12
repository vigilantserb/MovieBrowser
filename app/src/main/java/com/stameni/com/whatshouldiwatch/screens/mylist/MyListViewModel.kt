package com.stameni.com.whatshouldiwatch.screens.mylist

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.room.localData.DeleteMovieUseCase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie

class MyListViewModel(
    private val deleteSingleMovie: DeleteMovieUseCase
) : ViewModel() {
    val isMovieWatched = deleteSingleMovie.deletingSuccessful

    fun deleteSingleMovie(movie: Movie){
        deleteSingleMovie.deleteMovie(movie)
    }
}
