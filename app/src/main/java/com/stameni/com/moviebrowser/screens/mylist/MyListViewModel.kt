package com.stameni.com.moviebrowser.screens.mylist

import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.room.localData.CountMoviesByTypeUseCase
import io.reactivex.disposables.CompositeDisposable

class MyListViewModel(
    private val countMoviesByTypeUseCase: CountMoviesByTypeUseCase
) : ViewModel() {
    val disposable = CompositeDisposable()

    val toWatchCount = countMoviesByTypeUseCase.toWatchCount
    val watchedCount = countMoviesByTypeUseCase.watchedCount

    fun countMoviesByType() {
        disposable.add(countMoviesByTypeUseCase.countMoviesByList())
    }

}
