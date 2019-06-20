package com.stameni.com.whatshouldiwatch.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import com.stameni.com.whatshouldiwatch.data.networkData.search.SearchByTermUseCase
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val searchByTermUseCase: SearchByTermUseCase
) : ViewModel() {

    var disposable = CompositeDisposable()

    val fetchedData: LiveData<ArrayList<SearchItem>>
        get() {
            return searchByTermUseCase.fetchedData
        }

    val fetchError: LiveData<Exception>
        get() {
            return searchByTermUseCase.fetchError
        }

    fun searchByTerm(term: String) {
        disposable.add(searchByTermUseCase.searchByTerm(term))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        disposable.clear()
    }
}
