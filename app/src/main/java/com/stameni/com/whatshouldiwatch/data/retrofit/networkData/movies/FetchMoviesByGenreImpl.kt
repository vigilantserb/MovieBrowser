package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.SearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchMoviesByGenreImpl(
    private val movieApi: MovieApi
) : FetchMoviesByGenreUseCase {

    private val _totalPages = MutableLiveData<Int>()

    override val totalPages: LiveData<Int>
        get() = _totalPages

    private val _fetchError = MutableLiveData<Exception>()

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    private val _fetchedMovies = MutableLiveData<ArrayList<Movie>>()

    override val fetchedMovies: LiveData<ArrayList<Movie>>
        get() = _fetchedMovies

    override fun getMoviesWithGenre(genreId: Int, page: Int): Disposable {
        return movieApi.getGenreMovies(genreId, page)
            .subscribeOn(Schedulers.io())
            .map {
                emitTotalPages(it)
            }
            .map {
                formatResponseData(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGenreMoviesFetch(it) }, { onGenreMovieFetchFail(it) })
    }

    private fun emitTotalPages(response: Response<SearchSchema>): Response<SearchSchema> {
        if (response.body() != null)
            _totalPages.postValue(response.body()!!.totalPages)
        return response
    }

    private fun formatResponseData(response: Response<SearchSchema>): ArrayList<Movie> {
        val movieData = ArrayList<Movie>()
        if (response.body() != null) {
            response.body()!!.results.forEach {
                if (it.posterPath != null)
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

    private fun onGenreMovieFetchFail(exception: Throwable) {
        _fetchError.value = exception as Exception
    }

    private fun onGenreMoviesFetch(response: ArrayList<Movie>) {
        _fetchedMovies.value = response
    }
}