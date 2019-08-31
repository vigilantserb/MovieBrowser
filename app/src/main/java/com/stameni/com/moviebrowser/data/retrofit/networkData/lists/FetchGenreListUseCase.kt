package com.stameni.com.moviebrowser.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.data.models.Genre
import io.reactivex.disposables.Disposable

interface FetchGenreListUseCase {

    val genreListLiveData: LiveData<List<Genre>>

    val fetchError: MutableLiveData<String>

    fun getGenreList(): Disposable
}