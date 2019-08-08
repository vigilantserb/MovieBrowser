package com.stameni.com.whatshouldiwatch.data.networkData.person.actorMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import com.stameni.com.whatshouldiwatch.data.schemas.movie.SearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleActorMoviesUseCaseImpl(
    private val movieApi: MovieApi
) : FetchSingleActorMoviesUseCase {

    val _fetchedData = MutableLiveData<ArrayList<SearchItem>>()

    val _personMovieNumber = MutableLiveData<String>()

    override val fetchedData: LiveData<ArrayList<SearchItem>>
        get() = _fetchedData

    override val personMovieNumber: LiveData<String>
        get() = _personMovieNumber

    val _fetchError = MutableLiveData<java.lang.Exception>()

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun fetchSingleActorMovies(castId: Int): Disposable {
        return movieApi.getSingleActorMovies(castId)
            .subscribeOn(Schedulers.io())
            .map {
                getNumberOfMovies(it)
            }
            .map {
                formatResponse(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onDetailsFetched(it) }, { onDetailsFetchFailed(it) })
    }

    private fun getNumberOfMovies(response: Response<SearchSchema>): Response<SearchSchema> {
        if (response.isSuccessful) {
            if (response.body() != null) {
                val details = response.body()
                _personMovieNumber.postValue(details!!.totalResults.toString())
            }
        }
        return response
    }

    private fun onDetailsFetchFailed(it: Throwable?) {
        _fetchError.value = it as java.lang.Exception?
    }

    private fun onDetailsFetched(response: ArrayList<SearchItem>?) {
        if (!response.isNullOrEmpty()) _fetchedData.value = response
    }

    private fun formatResponse(response: Response<SearchSchema>): ArrayList<SearchItem> {
        val formattedData = ArrayList<SearchItem>()

        if (response.isSuccessful) {
            if (response.body() != null) {
                val details = response.body()
                details!!.results.forEach {
                    formattedData.add(SearchItem(it.title, it.posterPath, "Movie", it.releaseDate, it.id))
                }
            }
        }

        return formattedData
    }
}