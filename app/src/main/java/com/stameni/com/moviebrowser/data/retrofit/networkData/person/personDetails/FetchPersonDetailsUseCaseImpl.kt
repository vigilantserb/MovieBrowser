package com.stameni.com.moviebrowser.data.retrofit.networkData.person.personDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.data.models.person.PersonDetail
import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.schemas.person.singlePerson.SinglePersonSchema
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

    private val _fetchError = MutableLiveData<String>()
    override val fetchError: LiveData<String>
        get() = _fetchError

    override fun getPersonDetails(actorId: Int, type: String): Disposable {
        return movieApi.getPersonDetails(actorId)
            .subscribeOn(Schedulers.io())
            .map { formatResponse(it, type) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onDetailsFetched(it) }, { onDetailsFetchFailed(it) })
    }

    private fun onDetailsFetchFailed(exception: Throwable) {
        _fetchError.value = exception.localizedMessage
    }

    private fun onDetailsFetched(response: PersonDetail?) {
        response?.let { _personDetails.value = it }
    }

    private fun formatResponse(
        response: Response<SinglePersonSchema>,
        type: String
    ): PersonDetail? {
        if (response.isSuccessful) {
            val details = response.body()

            var birthdayString = details!!.birthday.split("-")
            val birthdayDate =
                LocalDate.of(
                    birthdayString[0].toInt(),
                    birthdayString[1].toInt(),
                    birthdayString[2].toInt()
                )

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
                if (type.contains(Constants.ACTOR_TYPE)) {
                    if (details.combinedCredits.cast != null) {
                        numberOfMovies += details.combinedCredits.cast.size
                    }
                } else if (type.contains(Constants.DIRECTOR_TYPE)) {
                    if (details.combinedCredits.crew != null) {
                        details.combinedCredits.crew.forEach {
                            if (it.department == "Writing" || it.department == "Directing" || it.department == "Production")
                                numberOfMovies++
                        }
                    }
                } else {
                    if (details.combinedCredits.crew != null) {
                        details.combinedCredits.crew.forEach {
                            if (it.department == "Writing" || it.department == "Directing" || it.department == "Production")
                                numberOfMovies++
                        }
                    }
                    if (details.combinedCredits.cast != null) {
                        numberOfMovies += details.combinedCredits.cast.size
                    }
                }
            }

            return PersonDetail(
                years,
                details.name,
                details.placeOfBirth,
                numberOfMovies,
                details.biography
            )
        }
        return null
    }
}