package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.recommendations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.SearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieRecommendationsImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieRecommendations {

    private val _fetchedData = MutableLiveData<ArrayList<Movie>>()
    override val fetchedData: LiveData<ArrayList<Movie>>
        get() = _fetchedData

    private val _fetchError = MutableLiveData<java.lang.Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun singleMovieRecommendations(movieId: Int): Disposable {
        return movieApi.getSingleMovieRecommendations(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                formatResponse(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onMovieRecommendationsFetch(it)
            }, { onMovieRecommendationsFetchFailed(it) })

    }

    private fun onMovieRecommendationsFetchFailed(it: Throwable) {
        _fetchError.value = it as java.lang.Exception
    }

    private fun onMovieRecommendationsFetch(response: ArrayList<Movie>) {
        _fetchedData.value = response
    }

    private fun formatResponse(it: Response<SearchSchema>): ArrayList<Movie> {
        val formattedData = ArrayList<Movie>()

        if (it.isSuccessful) {
            it.body()!!.results.forEach {
                if (it.posterPath != null)
                    formattedData.add(
                        Movie(
                            it.id,
                            it.title,
                            it.releaseDate,
                            "",
                            it.posterPath,
                            it.voteAverage
                        )
                    )
            }
        }

        return formattedData
    }
}