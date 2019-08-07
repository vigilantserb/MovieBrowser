package com.stameni.com.whatshouldiwatch.screens.singlePerson

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.actor.actorDetail.FetchPersonDetailsUseCase
import io.reactivex.disposables.CompositeDisposable

class SinglePersonActivityViewModel (
    private val fetchPersonDetailsUseCase: FetchPersonDetailsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val personDetails
        get() = fetchPersonDetailsUseCase.personDetails

    fun fetchPersonDetails(actorId: Int) {
        disposables.add(
            fetchPersonDetailsUseCase.getPersonDetails(actorId)
        )
    }
}