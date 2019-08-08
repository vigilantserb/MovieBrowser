package com.stameni.com.whatshouldiwatch.data.networkData.person.personDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.PersonDetail
import com.stameni.com.whatshouldiwatch.data.schemas.person.singlePerson.SinglePersonSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import retrofit2.Response


class FetchPersonDetailsUseCaseImpl(
    private val movieApi: MovieApi
) : FetchPersonDetailsUseCase {

    private val _personDetails = MutableLiveData<PersonDetail>()
    override val personDetails: LiveData<PersonDetail>
        get() = _personDetails

    private val _fetchError = MutableLiveData<java.lang.Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getPersonDetails(actorId: Int): Disposable {
        return movieApi.getPersonDetails(actorId)
            .subscribeOn(Schedulers.io())
            .map {
                formatResponse(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onDetailsFetched(it) }, { onDetailsFetchFailed(it) })
    }

    private fun onDetailsFetchFailed(exception: Throwable?) {
        _fetchError.value = exception as java.lang.Exception?
    }

    private fun onDetailsFetched(response: PersonDetail?) {
        if (response != null)
            _personDetails.value = response
    }

    private fun formatResponse(response: Response<SinglePersonSchema>): PersonDetail? {
        if (response.isSuccessful) {
            val details = response.body()

            var birthdayString = details!!.birthday.split("-")
            val birthdayDate =
                LocalDate.of(birthdayString[0].toInt(), birthdayString[1].toInt(), birthdayString[2].toInt())

            val deathdayString = details.deathday

            val years = if (deathdayString == null) {
                Period.between(birthdayDate, LocalDate.now()).years
            } else {
                val deathDate = details.deathday.split("-")
                Period.between(
                    birthdayDate,
                    LocalDate.of(deathDate[0].toInt(), deathDate[1].toInt(), deathDate[2].toInt())
                ).years
            }

            var numberOfMovies = 0

            if (details.combinedCredits != null) {
                if (details.combinedCredits.cast != null) {
                    numberOfMovies += details.combinedCredits.cast.size
                }
                if (details.combinedCredits.crew != null) {
                    numberOfMovies += details.combinedCredits.crew.size
                }
            }

            return PersonDetail(years, details.name, details.placeOfBirth, numberOfMovies, details.biography)
        }
        return null
    }
}