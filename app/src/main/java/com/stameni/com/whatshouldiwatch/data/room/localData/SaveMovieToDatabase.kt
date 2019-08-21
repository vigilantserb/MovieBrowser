package com.stameni.com.whatshouldiwatch.data.room.localData

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SaveMovieToDatabase(
    private val movieDatabase: MovieDatabase
) {
    val successMessage = MutableLiveData<String>()

    fun addMovieToDatabase(movieDetails: MovieDetails, listType: String): Disposable {
        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fetchSuccessful(it, movieDetails, listType) }, { e -> Timber.d(e) })
    }

    @SuppressLint("CheckResult")
    private fun fetchSuccessful(
        response: List<Movie>?,
        movieDetails: MovieDetails,
        listType: String
    ): Disposable {
        val listName = if (listType == "toWatch") "watch" else "watched"
        val movieId = movieDetails.movieId
        val movie: Movie? = response!!.find { it.movieId == movieId }
        if (movie == null) {
            return movieDatabase.movieDao()
                .insertMovie(
                    Movie(
                        movieDetails.movieTitle,
                        movieDetails.releaseDate,
                        movieDetails.genres,
                        movieDetails.posterPath,
                        movieDetails.movieId,
                        listType
                    )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successMessage.value = "${movieDetails.movieTitle} added to $listName list."
                }, { e -> Timber.d(e) })
        } else {
            return movieDatabase.movieDao()
                .updateMovie(
                    listType,
                    movieDetails.movieId
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successMessage.value = "${movieDetails.movieTitle} moved to $listName list."
                }, { e -> Timber.d(e) })
        }
    }
}