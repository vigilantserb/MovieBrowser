package com.stameni.com.whatshouldiwatch.data.networkData.person.personDetails

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.PersonDetail
import io.reactivex.disposables.Disposable

interface FetchPersonDetailsUseCase {

    val personDetails: LiveData<PersonDetail>

    val fetchError: LiveData<Exception>

    fun getPersonDetails(actorId: Int): Disposable
}