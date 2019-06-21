package com.stameni.com.whatshouldiwatch.data.networkData.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.Movie
import com.stameni.com.whatshouldiwatch.data.schemas.genre.GenreListSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.MovieListSchema
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import kotlin.collections.ArrayList


class FetchListMoviesUseCaseImpl(
    private var movieApi: MovieApi
) : FetchListMoviesUseCase {

    private var _fetchedMovies = MutableLiveData<ArrayList<Movie>>()
    private var _fetchError = MutableLiveData<Exception>()

    override val fetchedMovies: LiveData<ArrayList<Movie>>
        get() = _fetchedMovies

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getListMovies(listId: String): Disposable {
        var getGenreList = movieApi.getDbGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        var getListMovies = movieApi.getListMovies(listId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return Observable.zip(
            getGenreList,
            getListMovies,
            BiFunction<Response<GenreListSchema>, Response<MovieListSchema>, ArrayList<Movie>> { t1, t2 ->
                formatListMovies(t1, t2)
            })
            .map { orderByRating(it) }
            .flatMapIterable {
                it -> it
            }
            .buffer(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onListMovieFetch(ArrayList(it)) }, { onListMovieFetchFail(it as Exception) })
    }

    private fun orderByRating(movies: ArrayList<Movie>): ArrayList<Movie> {
        var sortedMovies: List<Movie>?
        sortedMovies = movies.sortedWith(compareByDescending { it.movieRating })
        return ArrayList((sortedMovies))
    }

    private fun formatListMovies(
        genreResponse: Response<GenreListSchema>,
        movies: Response<MovieListSchema>
    ): ArrayList<Movie> {
        val formattedMovies = ArrayList<Movie>()
        movies.body()!!.items.forEach { movie ->
            var genres = ""
            movie.genreIds.forEach { genreId ->
                genreResponse.body()!!.genres.forEach {
                    if (it.id == genreId) {
                        genres += "| ${it.name} "
                    }
                }
            }
            formattedMovies.add(Movie(movie.title, movie.releaseDate, genres, movie.posterPath, movie.voteAverage))
        }
        return formattedMovies
    }

    private fun onListMovieFetchFail(exception: Exception) {
        _fetchError.value = exception
    }

    private fun onListMovieFetch(response: ArrayList<Movie>) {
        _fetchedMovies.value = response
    }
}