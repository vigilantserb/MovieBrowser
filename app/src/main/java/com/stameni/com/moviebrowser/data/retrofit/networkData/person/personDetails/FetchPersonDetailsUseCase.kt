package com.stameni.com.moviebrowser.data.retrofit.networkData.person.personDetails

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.person.PersonDetail
import io.reactivex.disposables.Disposable

interface FetchPersonDetailsUseCase {

    val personDetails: LiveData<PersonDetail>

    val fetchError: LiveData<String>

    fun getPersonDetails(actorId: Int, type: String): Disposable
}