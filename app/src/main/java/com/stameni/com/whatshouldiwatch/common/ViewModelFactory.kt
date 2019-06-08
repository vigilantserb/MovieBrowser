package com.stameni.com.whatshouldiwatch.common

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMoviesViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val genreMoviesViewModel: GenreMoviesViewModel) :
    ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return genreMoviesViewModel as T
    }
}
