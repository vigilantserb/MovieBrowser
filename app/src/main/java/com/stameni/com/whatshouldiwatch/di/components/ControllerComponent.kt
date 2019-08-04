package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMovies
import com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist.MovieGridActivity
import com.stameni.com.whatshouldiwatch.screens.discover.nowPlaying.NowPlayingMovies
import com.stameni.com.whatshouldiwatch.screens.discover.topLists.TopListMovies
import com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist.MovieListActivity
import com.stameni.com.whatshouldiwatch.screens.discover.upcoming.UpcomingMovies
import com.stameni.com.whatshouldiwatch.screens.news.NewsFragment
import com.stameni.com.whatshouldiwatch.screens.search.SearchFragment
import com.stameni.com.whatshouldiwatch.screens.singleActor.SingleActorActivity
import com.stameni.com.whatshouldiwatch.screens.singleMovie.SingleMovieActivity
import dagger.Subcomponent

@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class, NetworkModule::class])
interface ControllerComponent {
    fun inject(genreMovies: GenreMovies)
    fun inject(movieListActivity: MovieListActivity)
    fun inject(movieGridActivity: MovieGridActivity)
    fun inject(upcomingMovies: UpcomingMovies)
    fun inject(nowPlayingMovies: NowPlayingMovies)
    fun inject(searchFragment: SearchFragment)
    fun inject(newsFragment: NewsFragment)
    fun inject(singleMovieActivity: SingleMovieActivity)
    fun inject(topListMovies: TopListMovies)
    fun inject(singleActorActivity: SingleActorActivity)
}