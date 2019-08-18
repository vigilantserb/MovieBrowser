package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.person.directorMovies

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import io.reactivex.disposables.Disposable

interface FetchSingleDirectorMovies {

    val fetchedData: LiveData<ArrayList<SearchItem>>

    val fetchError: LiveData<Exception>

    fun fetchSingleDirectorMovies(crewId: Int, page: Int): Disposable
}