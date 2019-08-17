package com.stameni.com.whatshouldiwatch.data.room.localData

import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UpdateMovieDataUseCase(
    private val movieDatabase: MovieDatabase
) {
    val successMessage = MutableLiveData<String>()

    fun updateMovieData(movieDetails: MovieDetails, listType: String): Disposable {
        val listName = if (listType == "toWatch") "watch" else "watched"
        return movieDatabase.movieDao()
            .updateMovie(
                listType,
                movieDetails.movieId
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successMessage.value = "${movieDetails.movieTitle} added to $listName list."
            }, { e -> Timber.d(e) })
    }
}