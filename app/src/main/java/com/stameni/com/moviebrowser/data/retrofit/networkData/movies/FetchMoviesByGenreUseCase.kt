package com.stameni.com.moviebrowser.data.retrofit.networkData.movies

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.movie.Movie
import io.reactivex.disposables.Disposable

interface FetchMoviesByGenreUseCase {

    val fetchedMovies: LiveData<ArrayList<Movie>>

    val fetchError: LiveData<String>

    val totalPages: LiveData<Int>

    fun getMoviesWithGenre(genreId: Int, page: Int): Disposable
}
