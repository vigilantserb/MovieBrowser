package com.stameni.com.whatshouldiwatch.screens.singleActor.biography

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.actor.actorDetail.FetchPersonDetailsUseCase
import io.reactivex.disposables.CompositeDisposable

class SingleActorBiographyViewModel(
    private val fetchPersonDetailsUseCase: FetchPersonDetailsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val actorDetails
        get() = fetchPersonDetailsUseCase.personDetails

    fun fetchActorDetails(actorId: Int) {
        disposables.add(
            fetchPersonDetailsUseCase.getPersonDetails(actorId)
        )
    }
}
