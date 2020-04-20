package com.stameni.com.moviebrowser.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.schemas.genre.GenreListSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.MovieListSchema
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class FetchListMoviesUseCaseImpl(
    private var movieApi: MovieApi
) : FetchListMoviesUseCase {

    private var _fetchedMovies = MutableLiveData<ArrayList<Movie>>()
    private var _fetchError = MutableLiveData<String>()

    override val fetchedMovies: LiveData<ArrayList<Movie>>
        get() = _fetchedMovies

    override val fetchError: LiveData<String>
        get() = _fetchError

    /**
     * Based on list ID that's passed in, corresponding movies with their genres will be returned
     * This method uses BiFunction to handle two calls to the server asynchronously, and only after
     * they're both done, the data is propagated to the UI
     * */
    override fun getListMovies(listId: String): Disposable {
        val getGenreList = movieApi.getDbGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val getListMovies = movieApi.getListMovies(listId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return Observable.zip(
            getGenreList,
            getListMovies,
            BiFunction<Response<GenreListSchema>, Response<MovieListSchema>, ArrayList<Movie>> { t1, t2 ->
                formatListMovies(t1, t2)
            })
            .map { orderByRating(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onListMovieFetch(ArrayList(it)) }, { onListMovieFetchFail(it as Exception) })
    }

    private fun orderByRating(movies: ArrayList<Movie>): ArrayList<Movie> {
        val sortedMovies: List<Movie>?
        sortedMovies = movies.sortedWith(compareByDescending { it.movieRating })
        return ArrayList((sortedMovies))
    }

    private fun formatListMovies(
        genreResponse: Response<GenreListSchema>,
        movies: Response<MovieListSchema>
    ): ArrayList<Movie> {
        val formattedMovies = ArrayList<Movie>()

        movies.body()?.items?.let {
            it.forEach {movie ->
                val genreStringArray = ArrayList<String>()
                movie.genreIds.forEach { genreId ->
                    genreResponse.body()?.genres
                        ?.find { it.id == genreId }?.name
                        ?.let { genreName -> genreStringArray.add(genreName) }
                }

                val genres = genreStringArray.joinToString(", ")

                movie.title?.let {
                    formattedMovies.add(
                        Movie(
                            movie.id,
                            movie.title,
                            movie.releaseDate,
                            genres,
                            movie.posterPath,
                            movie.voteAverage
                        )
                    )
                }
            }
        }
        return formattedMovies
    }

    private fun onListMovieFetchFail(exception: Exception) {
        _fetchError.value = exception.localizedMessage
    }

    private fun onListMovieFetch(response: ArrayList<Movie>) {
        _fetchedMovies.value = response
    }
}