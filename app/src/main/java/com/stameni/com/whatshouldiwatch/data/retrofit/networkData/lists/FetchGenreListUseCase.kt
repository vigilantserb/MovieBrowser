package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Genre
import io.reactivex.disposables.Disposable

interface FetchGenreListUseCase {

    val genreListLiveData: LiveData<List<Genre>>

    val fetchError: LiveData<Exception>

    fun getGenreList(): Disposable
}