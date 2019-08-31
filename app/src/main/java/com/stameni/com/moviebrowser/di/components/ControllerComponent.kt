package com.stameni.com.moviebrowser.di.components

import com.stameni.com.moviebrowser.di.modules.ControllerModule
import com.stameni.com.moviebrowser.di.modules.NetworkModule
import com.stameni.com.moviebrowser.di.modules.ViewModelModule
import com.stameni.com.moviebrowser.screens.discover.genre.GenreMovies
import com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist.MovieGridActivity
import com.stameni.com.moviebrowser.screens.discover.nowPlaying.NowPlayingMovies
import com.stameni.com.moviebrowser.screens.discover.topLists.TopListMovies
import com.stameni.com.moviebrowser.screens.discover.topLists.movielist.MovieListActivity
import com.stameni.com.moviebrowser.screens.discover.upcoming.UpcomingMovies
import com.stameni.com.moviebrowser.screens.mylist.MyListFragment
import com.stameni.com.moviebrowser.screens.mylist.toWatch.ToWatchActivity
import com.stameni.com.moviebrowser.screens.mylist.watched.WatchedActivity
import com.stameni.com.moviebrowser.screens.news.NewsFragment
import com.stameni.com.moviebrowser.screens.search.SearchFragment
import com.stameni.com.moviebrowser.screens.settings.SettingsFragment
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity
import com.stameni.com.moviebrowser.screens.singlePerson.SinglePersonActivity
import com.stameni.com.moviebrowser.screens.singlePerson.appearances.SinglePersonAppearancesFragment
import com.stameni.com.moviebrowser.screens.singlePerson.biography.SingleActorBiographyFragment
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
    fun inject(singlePersonActivity: SinglePersonActivity)
    fun inject(singleActorBiographyFragment: SingleActorBiographyFragment)
    fun inject(singlePersonAppearancesFragment: SinglePersonAppearancesFragment)
    fun inject(myListFragment: MyListFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(toWatchActivity: ToWatchActivity)
    fun inject(watchedActivity: WatchedActivity)
}