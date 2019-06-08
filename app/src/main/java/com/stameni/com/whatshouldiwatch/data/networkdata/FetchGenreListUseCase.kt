package com.stameni.com.whatshouldiwatch.data.networkdata

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Genre
import io.reactivex.disposables.Disposable

interface FetchGenreListUseCase {

    val genreListLiveData: LiveData<List<Genre>>

    fun getGenreList(): Disposable
}