package com.stameni.com.whatshouldiwatch.screens.singlePerson.biography

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.networkData.person.personDetails.FetchPersonDetailsUseCase
import io.reactivex.disposables.CompositeDisposable

class SingleActorBiographyViewModel(
    private val fetchPersonDetailsUseCase: FetchPersonDetailsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val fetchedBiography
        get() = fetchPersonDetailsUseCase.personDetails

    fun fetchPersonBiography(actorId: Int) {
        disposables.add(
            fetchPersonDetailsUseCase.getPersonDetails(actorId)
        )
    }
}
