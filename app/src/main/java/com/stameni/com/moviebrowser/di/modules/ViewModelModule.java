package com.stameni.com.moviebrowser.di.modules;

import androidx.lifecycle.ViewModel;

import com.stameni.com.moviebrowser.common.ViewModelFactory;
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchGenreListUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchListMoviesUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.FetchMoviesByGenreUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.FetchNowPlayingMovies;
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.FetchUpcomingMovies;
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.details.FetchSingleMovieDetails;
import com.stameni.com.moviebrowser.data.retrofit.networkData.news.FetchEntertainmentNewsUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies.FetchSingleActorMoviesUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.directorMovies.FetchSingleDirectorMovies;
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.personDetails.FetchPersonDetailsUseCase;
import com.stameni.com.moviebrowser.data.retrofit.networkData.search.SearchByTermUseCase;
import com.stameni.com.moviebrowser.data.room.localData.CountMoviesByTypeUseCase;
import com.stameni.com.moviebrowser.data.room.localData.DeleteMovieUseCase;
import com.stameni.com.moviebrowser.data.room.localData.FetchMovieListUseCase;
import com.stameni.com.moviebrowser.data.room.localData.ImportMovieListFromCsvUseCase;
import com.stameni.com.moviebrowser.data.room.localData.SaveMovieToDatabase;
import com.stameni.com.moviebrowser.data.room.localData.UpdateMovieDataUseCase;
import com.stameni.com.moviebrowser.screens.discover.genre.GenreMoviesViewModel;
import com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist.MovieGridViewModel;
import com.stameni.com.moviebrowser.screens.discover.nowPlaying.NowPlayingMoviesViewModel;
import com.stameni.com.moviebrowser.screens.discover.topLists.movielist.MovieListViewModel;
import com.stameni.com.moviebrowser.screens.discover.upcoming.UpcomingMoviesViewModel;
import com.stameni.com.moviebrowser.screens.mylist.MyListViewModel;
import com.stameni.com.moviebrowser.screens.mylist.toWatch.ToWatchViewModel;
import com.stameni.com.moviebrowser.screens.mylist.watched.WatchedViewModel;
import com.stameni.com.moviebrowser.screens.news.NewsViewModel;
import com.stameni.com.moviebrowser.screens.search.SearchViewModel;
import com.stameni.com.moviebrowser.screens.settings.SettingsViewModel;
import com.stameni.com.moviebrowser.screens.settings.useCases.ClearPhoneCashUseCase;
import com.stameni.com.moviebrowser.screens.settings.useCases.CreateCsvFileUseCase;
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieViewModel;
import com.stameni.com.moviebrowser.screens.singlePerson.SinglePersonActivityViewModel;
import com.stameni.com.moviebrowser.screens.singlePerson.appearances.SinglePersonAppearancesViewModel;
import com.stameni.com.moviebrowser.screens.singlePerson.biography.SingleActorBiographyViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

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

    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    ViewModel searchViewModel(SearchByTermUseCase searchByTermUseCase) {
        return new SearchViewModel(searchByTermUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    ViewModel newsViewModel(FetchEntertainmentNewsUseCase fetchEntertainmentNewsUseCase) {
        return new NewsViewModel(fetchEntertainmentNewsUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SingleMovieViewModel.class)
    ViewModel singleMovieViewModel(
            FetchSingleMovieDetails fetchSingleMovieDetails,
            SaveMovieToDatabase saveMovieToDatabase) {
        return new SingleMovieViewModel(
                fetchSingleMovieDetails, saveMovieToDatabase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SingleActorBiographyViewModel.class)
    ViewModel singleActorBiographyViewModel(
            FetchPersonDetailsUseCase fetchPersonDetailsUseCase) {
        return new SingleActorBiographyViewModel(fetchPersonDetailsUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SinglePersonActivityViewModel.class)
    ViewModel singleActorActivityViewModel(
            FetchPersonDetailsUseCase fetchPersonDetailsUseCase) {
        return new SinglePersonActivityViewModel(fetchPersonDetailsUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SinglePersonAppearancesViewModel.class)
    ViewModel singleActorAppearancesViewModel(
            FetchSingleActorMoviesUseCase fetchSingleActorMoviesUseCase,
            FetchSingleDirectorMovies fetchSingleDirectorMovies) {
        return new SinglePersonAppearancesViewModel(fetchSingleActorMoviesUseCase, fetchSingleDirectorMovies);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MyListViewModel.class)
    ViewModel myListViewModel(
            CountMoviesByTypeUseCase countMoviesByTypeUseCase) {
        return new MyListViewModel(countMoviesByTypeUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(ToWatchViewModel.class)
    ViewModel toWatchViewModel(
            DeleteMovieUseCase deleteMovieUseCase,
            FetchMovieListUseCase fetchMovieListUseCase,
            UpdateMovieDataUseCase updateMovieDataUseCase) {
        return new ToWatchViewModel(deleteMovieUseCase, fetchMovieListUseCase, updateMovieDataUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(WatchedViewModel.class)
    ViewModel watchedViewModel(
            DeleteMovieUseCase deleteMovieUseCase,
            FetchMovieListUseCase fetchMovieListUseCase,
            UpdateMovieDataUseCase updateMovieDataUseCase) {
        return new WatchedViewModel(deleteMovieUseCase, fetchMovieListUseCase, updateMovieDataUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    ViewModel SettingsViewModel(
            CreateCsvFileUseCase createCsvFileUseCase,
            ClearPhoneCashUseCase clearPhoneCashUseCase,
            ImportMovieListFromCsvUseCase importMovieListFromCsv) {
        return new SettingsViewModel(createCsvFileUseCase, clearPhoneCashUseCase, importMovieListFromCsv);
    }
}
