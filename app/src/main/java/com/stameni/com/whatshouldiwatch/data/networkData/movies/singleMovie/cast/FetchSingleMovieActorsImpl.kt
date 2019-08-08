package com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.movie.Actor
import com.stameni.com.whatshouldiwatch.data.schemas.movie.cast.SingleMovieCastSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieActorsImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieActors {

    private val _fetchedActors = MutableLiveData<ArrayList<Actor>>()

    override val fetchedActors: LiveData<ArrayList<Actor>>
        get() = _fetchedActors

    private val _fetchError = MutableLiveData<Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getSingleMovieActors(movieId: Int): Disposable {
        return movieApi.getSingleMovieActors(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                formatResponseData(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onActorsFetched(it) }, { onActorsFetchFailed(it) })
    }

    private fun onActorsFetchFailed(it: Throwable?) {
        _fetchError.value = it as Exception?
    }

    private fun onActorsFetched(response: ArrayList<Actor>?) {
        _fetchedActors.value = response
    }

    private fun formatResponseData(response: Response<SingleMovieCastSchema>): ArrayList<Actor> {
        val formattedData = ArrayList<Actor>()

        if (response.isSuccessful) {
            if (response.body() != null) {
                response.body()!!.cast.forEach {
                    formattedData.add(
                        Actor(
                            it.id,
                            it.name,
                            it.character,
                            it.profilePath
                        )
                    )
                }
            }
        }

        return formattedData
    }
}