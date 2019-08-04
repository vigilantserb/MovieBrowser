package com.stameni.com.whatshouldiwatch.data.networkData.actor.actorDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.ActorDetail
import com.stameni.com.whatshouldiwatch.data.schemas.person.singlePerson.SinglePersonSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import retrofit2.Response


class FetchSingleActorDetailsUseCaseImpl(
    private val movieApi: MovieApi
) : FetchSingleActorDetailsUseCase {

    private val _actorDetails = MutableLiveData<ActorDetail>()
    override val actorDetails: LiveData<ActorDetail>
        get() = _actorDetails

    private val _fetchError = MutableLiveData<java.lang.Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getActorDetails(actorId: Int): Disposable {
        return movieApi.getSingleActorDetails(actorId)
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

    private fun onDetailsFetched(response: ActorDetail?) {
        if (response != null)
            _actorDetails.value = response
    }

    private fun formatResponse(response: Response<SinglePersonSchema>): ActorDetail? {
        if (response.isSuccessful) {
            val details = response.body()

            var birthdayString = details!!.birthday.split("-")
            val birthdayDate =LocalDate.of(birthdayString[0].toInt(), birthdayString[1].toInt(), birthdayString[2].toInt())

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

            return ActorDetail(years, details.name, details.placeOfBirth, "", details.biography)
        }
        return null
    }
}