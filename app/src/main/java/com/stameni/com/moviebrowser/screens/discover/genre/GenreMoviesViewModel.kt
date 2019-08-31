package com.stameni.com.moviebrowser.screens.discover.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.models.Genre
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchGenreListUseCase
import io.reactivex.disposables.CompositeDisposable

class GenreMoviesViewModel(private val fetchGenreListUseCase: FetchGenreListUseCase) : ViewModel() {

    val fetchedGenres: LiveData<List<Genre>>
        get() {
            return fetchGenreListUseCase.genreListLiveData
        }

    val fetchError: LiveData<String>
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
