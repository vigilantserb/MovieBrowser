package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.SingleMovieDetailsSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieDetailsImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieDetails {

    private val _fetchError = MutableLiveData<java.lang.Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    private val _movieDetails = MutableLiveData<MovieDetails>()
    override val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    override fun getSingleMovieDetails(movieId: Int): Disposable {
        return movieApi.getSingleMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                formatResponse(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onDetailsFetched(it) }, { onDetailsFetchFailed(it) })
    }

    private fun onDetailsFetchFailed(it: Throwable?) {
        _fetchError.value = it as java.lang.Exception?
    }

    private fun onDetailsFetched(respone: MovieDetails?) {
        _movieDetails.value = respone
    }

    private fun formatResponse(it: Response<SingleMovieDetailsSchema>): MovieDetails? {
        if (it.isSuccessful) {
            val data = it.body()!!
            val genreString = ArrayList<String>()
            data.genres.forEach { genreString.add(it.name) }
            val genres = genreString.joinToString(", ")
            var directorName = ""
            var directorImageUrl = ""
            var directorId = 0
            data.credits.crew.forEach {
                if (it.job == "Director") {
                    directorName = it.name
                    directorImageUrl = it.profilePath
                    directorId = it.id
                }
            }
            return MovieDetails(
                data.id,
                data.title,
                data.posterPath,
                data.overview,
                data.imdbId,
                data.voteAverage,
                data.releaseDate,
                data.runtime,
                genres,
                directorName,
                directorImageUrl,
                directorId
            )
        }
        return null
    }

}