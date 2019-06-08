package com.stameni.com.whatshouldiwatch.screens.discover.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.models.Genre
import com.stameni.com.whatshouldiwatch.data.networkdata.FetchGenreListUseCase
import io.reactivex.disposables.CompositeDisposable

class GenreMoviesViewModel(private val fetchGenreListUseCase: FetchGenreListUseCase) : ViewModel() {

    var fetchedGenres: LiveData<List<Genre>> = MutableLiveData<List<Genre>>()
        get() {
            return fetchGenreListUseCase.genreListLiveData
        }

    var disposable = CompositeDisposable()

    fun getGenreList() {
        disposable.add(fetchGenreListUseCase.getGenreList())
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}
