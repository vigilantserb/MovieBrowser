package com.stameni.com.whatshouldiwatch.screens.singlePerson

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.retrofit.networkData.person.personDetails.FetchPersonDetailsUseCase
import io.reactivex.disposables.CompositeDisposable

class SinglePersonActivityViewModel(
    private val fetchPersonDetailsUseCase: FetchPersonDetailsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val personDetails
        get() = fetchPersonDetailsUseCase.personDetails

    val fetchError
        get() = fetchPersonDetailsUseCase.fetchError

    fun fetchPersonDetails(actorId: Int, type: String) {
        disposables.add(
            fetchPersonDetailsUseCase.getPersonDetails(actorId, type)
        )
    }
}