package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMovies
import com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist.MovieListActivity
import dagger.Subcomponent

@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class, NetworkModule::class])
interface ControllerComponent {
    fun inject(genreMovies: GenreMovies)
    fun inject(genreMovies: MovieListActivity)
}