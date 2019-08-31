package com.stameni.com.moviebrowser.di.modules

import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.NewsApi
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchGenreListUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchGenreListUseCaseImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchListMoviesUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.lists.FetchListMoviesUseCaseImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.*
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.cast.FetchSingleMovieActors
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.cast.FetchSingleMovieActorsImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.certification.FetchSingleMovieCertification
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.certification.FetchSingleMovieCertificationImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.details.FetchSingleMovieDetails
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.details.FetchSingleMovieDetailsImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.images.FetchSingleMovieImages
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.images.FetchSingleMovieImagesImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendations
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendationsImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.trailer.FetchSingleMovieTrailer
import com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.trailer.FetchSingleMovieTrailerImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.news.FetchEntertainmentNewsUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.news.FetchEntertainmentNewsUseCaseImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies.FetchSingleActorMoviesUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.actorMovies.FetchSingleActorMoviesUseCaseImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.directorMovies.FetchSingleDirectorMovies
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.directorMovies.FetchSingleDirectorMoviesImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.personDetails.FetchPersonDetailsUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.person.personDetails.FetchPersonDetailsUseCaseImpl
import com.stameni.com.moviebrowser.data.retrofit.networkData.search.SearchByTermUseCase
import com.stameni.com.moviebrowser.data.retrofit.networkData.search.SearchByTermUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun getFetchGenresUseCase(movieApi: MovieApi): FetchGenreListUseCase {
        return FetchGenreListUseCaseImpl(movieApi)
    }

    @Provides
    fun getFetchListMoviesUseCase(movieApi: MovieApi): FetchListMoviesUseCase {
        return FetchListMoviesUseCaseImpl(movieApi)
    }

    @Provides
    fun getFetchMoviesByGenreUseCase(movieApi: MovieApi): FetchMoviesByGenreUseCase {
        return FetchMoviesByGenreImpl(movieApi)
    }

    @Provides
    fun getFetchUpcomingMoviesUseCase(movieApi: MovieApi): FetchUpcomingMovies =
        FetchUpcomingMoviesImpl(movieApi)

    @Provides
    fun getFetchNowPlayingMoviesUseCase(movieApi: MovieApi): FetchNowPlayingMovies =
        FetchNowPlayingMoviesImpl(movieApi)

    @Provides
    fun getSearchByTermUseCase(movieApi: MovieApi): SearchByTermUseCase =
        SearchByTermUseCaseImpl(movieApi)

    @Provides
    fun getEntertainmentNewsUseCase(newsApi: NewsApi): FetchEntertainmentNewsUseCase =
        FetchEntertainmentNewsUseCaseImpl(newsApi)

    @Provides
    fun getSingleMovieImages(): FetchSingleMovieImages = FetchSingleMovieImagesImpl()

    @Provides
    fun getSingleMovieActors(): FetchSingleMovieActors =
        FetchSingleMovieActorsImpl()

    @Provides
    fun getSingleMovieRecommendations(): FetchSingleMovieRecommendations =
        FetchSingleMovieRecommendationsImpl()

    @Provides
    fun getSingleMoviesDetails(
        movieApi: MovieApi,
        fetchSingleMovieImages: FetchSingleMovieImages,
        fetchSingleMovieActors: FetchSingleMovieActors,
        fetchSingleMovieRecommendations: FetchSingleMovieRecommendations,
        fetchSingleMovieTrailer: FetchSingleMovieTrailer,
        fetchSingleMovieCertification: FetchSingleMovieCertification
    ): FetchSingleMovieDetails =
        FetchSingleMovieDetailsImpl(
            movieApi,
            fetchSingleMovieImages,
            fetchSingleMovieActors,
            fetchSingleMovieRecommendations,
            fetchSingleMovieTrailer,
            fetchSingleMovieCertification
        )

    @Provides
    fun getActorDetails(movieApi: MovieApi): FetchPersonDetailsUseCase =
        FetchPersonDetailsUseCaseImpl(
            movieApi
        )

    @Provides
    fun getActorMovies(movieApi: MovieApi): FetchSingleActorMoviesUseCase =
        FetchSingleActorMoviesUseCaseImpl(
            movieApi
        )

    @Provides
    fun getSingleMovieCerfitication(): FetchSingleMovieCertification =
        FetchSingleMovieCertificationImpl()

    @Provides
    fun getSingleMovieTrailer(): FetchSingleMovieTrailer = FetchSingleMovieTrailerImpl()

    @Provides
    fun getSingleDirectorMovies(movieApi: MovieApi): FetchSingleDirectorMovies =
        FetchSingleDirectorMoviesImpl(
            movieApi
        )
}
