package com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.data.models.SearchItem
import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.SearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleActorMoviesUseCaseImpl(
    private val movieApi: MovieApi
) : FetchSingleActorMoviesUseCase {

    val _fetchedData = MutableLiveData<ArrayList<SearchItem>>()

    override val fetchedData: LiveData<ArrayList<SearchItem>>
        get() = _fetchedData

    val _fetchError = MutableLiveData<java.lang.Exception>()

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun fetchSingleActorMovies(castId: Int, page: Int): Disposable {
        return movieApi.getSingleActorMovies(castId, page)
            .subscribeOn(Schedulers.io())
            .map { formatResponse(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onDetailsFetched(it) }, { onDetailsFetchFailed(it) })
    }

    private fun onDetailsFetchFailed(it: Throwable?) {
        _fetchError.value = it as java.lang.Exception?
    }

    private fun onDetailsFetched(response: ArrayList<SearchItem>?) {
        if (!response.isNullOrEmpty()) _fetchedData.value = response
    }

    private fun formatResponse(response: Response<SearchSchema>): ArrayList<SearchItem> {
        val formattedData = ArrayList<SearchItem>()
        response.body()?.results?.let {
            it.forEach {
                formattedData.add(
                    SearchItem(
                        it.title,
                        it.posterPath,
                        "Movie",
                        it.releaseDate,
                        it.id
                    )
                )
            }
        }
        return formattedData
    }
}