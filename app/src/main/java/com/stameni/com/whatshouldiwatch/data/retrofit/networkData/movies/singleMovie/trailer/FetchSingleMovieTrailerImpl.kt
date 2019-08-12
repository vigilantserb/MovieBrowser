package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.trailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.trailer.SingleMovieVideosSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieTrailerImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieTrailer {

    private val _fetchedTrailerUrl = MutableLiveData<String>()
    override val fetchedTrailerUrl: LiveData<String>
        get() = _fetchedTrailerUrl


    private val _fetchError = MutableLiveData<Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getSingleMovieTrailer(movieId: Int): Disposable {
        return movieApi.getSingleMovieTrailer(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                getTrailerLink(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onTrailerFetched(it) }, { onTrailerFetchFail(it) })
    }

    private fun onTrailerFetchFail(exception: Throwable?) {
        _fetchError.value = exception as Exception?
    }

    private fun onTrailerFetched(response: String?) {
        if(!response.isNullOrEmpty()) _fetchedTrailerUrl.value = response
    }


    private fun getTrailerLink(response: Response<SingleMovieVideosSchema>): String {
        var link = ""

        if(response.isSuccessful){
            response.body()!!.results.forEach {
                if(it.site == "YouTube" && it.type == "Trailer") link = it.key
            }
        }

        return link
    }
}