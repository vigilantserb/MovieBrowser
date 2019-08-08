package com.stameni.com.whatshouldiwatch.data.networkData.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import com.stameni.com.whatshouldiwatch.data.schemas.movie.SearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchNowPlayingMoviesImpl(
    private val movieApi: MovieApi
) : FetchNowPlayingMovies {

    private val _fetchedMovies = MutableLiveData<ArrayList<Movie>>()

    override val fetchedMovies: LiveData<ArrayList<Movie>>
        get() = _fetchedMovies

    private val _totalPages = MutableLiveData<Int>()

    override val totalPages: LiveData<Int>
        get() = _totalPages

    private val _fetchError = MutableLiveData<Exception>()

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getNowPlayingMovies(page: Int): Disposable {
        return movieApi.getNowPlayingMovies(page)
            .subscribeOn(Schedulers.io())
            .map {
                getTotalpages(it)
            }
            .map {
                formatResponseData(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onNowPlayingMoviesFetch(it) }, { onNowPlayingMoviesFetchFail(it) })
    }

    private fun getTotalpages(response: Response<SearchSchema>): Response<SearchSchema> {
        if (response.body() != null)
            _totalPages.postValue(response.body()!!.totalPages)
        return response
    }

    private fun onNowPlayingMoviesFetchFail(exception: Throwable) {
        _fetchError.value = exception as java.lang.Exception
    }

    private fun onNowPlayingMoviesFetch(movies: ArrayList<Movie>) {
        _fetchedMovies.value = movies
    }

    private fun formatResponseData(response: Response<SearchSchema>): ArrayList<Movie> {
        val movieData = ArrayList<Movie>()

        if (response.body() != null) {
            response.body()!!.results.forEach {
                movieData.add(
                    Movie(
                        it.id,
                        it.title,
                        "",
                        "",
                        it.posterPath,
                        0.0
                    )
                )
            }
        }

        return movieData
    }
}