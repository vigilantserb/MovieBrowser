package com.stameni.com.moviebrowser.data.retrofit.networkData.search

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.SearchItem
import io.reactivex.disposables.Disposable

interface SearchByTermUseCase {

    val fetchedData: LiveData<ArrayList<SearchItem>>

    val fetchError: LiveData<String>

    fun searchByTerm(term: String): Disposable
}