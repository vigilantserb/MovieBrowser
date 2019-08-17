package com.stameni.com.whatshouldiwatch.data.room.localData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FetchMovieListUseCase(
    private val movieDatabase: MovieDatabase
) {
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error

    private val _fetchedMovies = MutableLiveData<List<Movie>>()
    val fetchedMovies: LiveData<List<Movie>>
        get() = _fetchedMovies

    fun fetchMovies(listType: String): Disposable {
        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fetchSuccessful(it, listType) }, { fetchFail(it) })
    }

    private fun fetchSuccessful(
        response: List<Movie>,
        listType: String
    ) {
        val formattedData = ArrayList<Movie>()
        response.forEach {
            if (it.listType == listType)
                formattedData.add(it)
        }
        _fetchedMovies.value = formattedData
    }

    private fun fetchFail(it: Throwable) {
        _error.value = it as Exception
    }
}