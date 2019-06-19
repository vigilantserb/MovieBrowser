package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMovies
import com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist.MovieGridActivity
import com.stameni.com.whatshouldiwatch.screens.discover.nowPlaying.NowPlayingMovies
import com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist.MovieListActivity
import com.stameni.com.whatshouldiwatch.screens.discover.upcoming.UpcomingMovies
import dagger.Subcomponent

@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class, NetworkModule::class])
interface ControllerComponent {
    fun inject(genreMovies: GenreMovies)
    fun inject(movieListActivity: MovieListActivity)
    fun inject(movieGridActivity: MovieGridActivity)
    fun inject(upcomingMovies: UpcomingMovies)
    fun inject(nowPlayingMovies: NowPlayingMovies)
}