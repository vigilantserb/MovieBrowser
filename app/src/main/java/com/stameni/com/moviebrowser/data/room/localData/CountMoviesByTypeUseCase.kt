package com.stameni.com.moviebrowser.data.room.localData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.data.room.MovieDatabase
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountMoviesByTypeUseCase(
    private val movieDatabase: MovieDatabase
) {
    private val _toWatchCount = MutableLiveData<Int>()
    val toWatchCount: LiveData<Int> get() = _toWatchCount

    private val _watchedCount = MutableLiveData<Int>()
    val watchedCount: LiveData<Int> get() = _watchedCount

    fun countMoviesByList(): Disposable {
        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fetchSuccessful(it)
            }, { println("Failure") })
    }

    private fun fetchSuccessful(response: List<Movie>?) {
        var toWatchCount = 0
        var watchedCount = 0
        response?.forEach { singleMovie ->
            if (singleMovie.listType == "toWatch") toWatchCount++
            else watchedCount++
        }
        _toWatchCount.value = toWatchCount
        _watchedCount.value = watchedCount
    }
}