package com.stameni.com.moviebrowser.data.room.localData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.data.room.MovieDatabase
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeleteMovieUseCase(
    private val movieDatabase: MovieDatabase
) {
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error

    private val _deletingSuccessful = MutableLiveData<Int>()
    val deletingSuccessful: LiveData<Int>
        get() = _deletingSuccessful

    fun deleteMovie(movie: Movie): Disposable {
        return movieDatabase.movieDao()
            .deleteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ deletingSuccesful()}, { deletingFailed(it)})
    }

    private fun deletingSuccesful() {
        _deletingSuccessful.value = Constants.SUCCESS
    }

    private fun deletingFailed(it: Throwable) {
        _error.value = it as Exception
    }
}