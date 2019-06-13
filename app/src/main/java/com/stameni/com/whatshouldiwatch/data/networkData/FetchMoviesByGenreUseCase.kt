package com.stameni.com.whatshouldiwatch.data.networkData

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.Movie
import io.reactivex.disposables.Disposable
import java.lang.Exception

interface FetchMoviesByGenreUseCase {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val fetchError: LiveData<Exception>

    fun getMoviesWithGenre(genreId: Int, page: Int): Disposable
}
