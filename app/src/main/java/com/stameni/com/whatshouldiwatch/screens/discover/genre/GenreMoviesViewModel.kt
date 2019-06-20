package com.stameni.com.whatshouldiwatch.screens.discover.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.models.Genre
import com.stameni.com.whatshouldiwatch.data.networkData.lists.FetchGenreListUseCase
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class GenreMoviesViewModel(private val fetchGenreListUseCase: FetchGenreListUseCase) : ViewModel() {

    val fetchedGenres: LiveData<List<Genre>>
        get() {
            return fetchGenreListUseCase.genreListLiveData
        }

    val fetchError: LiveData<Exception>
        get() {
            return fetchGenreListUseCase.fetchError
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
