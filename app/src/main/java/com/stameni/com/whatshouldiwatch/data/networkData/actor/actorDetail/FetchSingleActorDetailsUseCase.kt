package com.stameni.com.whatshouldiwatch.data.networkData.actor.actorDetail

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.ActorDetail
import io.reactivex.disposables.Disposable

interface FetchSingleActorDetailsUseCase {

    val actorDetails: LiveData<ActorDetail>

    val fetchError: LiveData<Exception>

    fun getActorDetails(actorId: Int): Disposable
}