package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.Genre
import io.reactivex.disposables.Disposable

interface FetchGenreListUseCase {

    val genreListLiveData: LiveData<List<Genre>>

    val fetchError: MutableLiveData<String>

    fun getGenreList(): Disposable
}