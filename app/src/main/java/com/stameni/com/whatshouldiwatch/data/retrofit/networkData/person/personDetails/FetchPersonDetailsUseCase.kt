package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.person.personDetails

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.person.PersonDetail
import io.reactivex.disposables.Disposable

interface FetchPersonDetailsUseCase {

    val personDetails: LiveData<PersonDetail>

    val fetchError: LiveData<String>

    fun getPersonDetails(actorId: Int, type: String): Disposable
}