package com.stameni.com.whatshouldiwatch.screens.singleActor

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.actor.actorDetail.FetchSingleActorDetailsUseCase
import io.reactivex.disposables.CompositeDisposable

class SingleActorActivityViewModel (
    private val fetchSingleActorDetailsUseCase: FetchSingleActorDetailsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val actorDetails
        get() = fetchSingleActorDetailsUseCase.actorDetails

    fun fetchActorDetails(actorId: Int) {
        disposables.add(
            fetchSingleActorDetailsUseCase.getActorDetails(actorId)
        )
    }
}