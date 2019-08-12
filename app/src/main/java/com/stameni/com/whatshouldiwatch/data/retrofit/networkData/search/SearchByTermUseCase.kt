package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.search

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import io.reactivex.disposables.Disposable

interface SearchByTermUseCase {

    val fetchedData: LiveData<ArrayList<SearchItem>>

    val fetchError: LiveData<Exception>

    fun searchByTerm(term: String): Disposable
}