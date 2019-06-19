package com.stameni.com.whatshouldiwatch.di.modules;

import androidx.lifecycle.ViewModel;
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory;
import com.stameni.com.whatshouldiwatch.data.networkData.*;
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMoviesViewModel;
import com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist.MovieGridViewModel;
import com.stameni.com.whatshouldiwatch.screens.discover.nowPlaying.NowPlayingMoviesViewModel;
import com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist.MovieListViewModel;
import com.stameni.com.whatshouldiwatch.screens.discover.upcoming.UpcomingMoviesViewModel;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import javax.inject.Provider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(GenreMoviesViewModel.class)
    ViewModel genreMoviesViewModel(FetchGenreListUseCase fetchGenreListUseCase) {
        return new GenreMoviesViewModel(fetchGenreListUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    ViewModel movieListViewModel(FetchListMoviesUseCase fetchGenreListUseCase) {
        return new MovieListViewModel(fetchGenreListUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieGridViewModel.class)
    ViewModel movieGridViewModel(FetchMoviesByGenreUseCase fetchMoviesByGenreUseCase) {
        return new MovieGridViewModel(fetchMoviesByGenreUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(UpcomingMoviesViewModel.class)
    ViewModel upcomingMoviesViewModel(FetchUpcomingMovies fetchUpcomingMovies) {
        return new UpcomingMoviesViewModel(fetchUpcomingMovies);
    }

    @Provides
    @IntoMap
    @ViewModelKey(NowPlayingMoviesViewModel.class)
    ViewModel nowPlayingViewModel(FetchNowPlayingMovies fetchNowPlayingMovies) {
        return new NowPlayingMoviesViewModel(fetchNowPlayingMovies);
    }
}
