package com.stameni.com.whatshouldiwatch.di.modules

import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.NEWS_API_KEY
import com.stameni.com.whatshouldiwatch.data.NEWS_BASE_URL
import com.stameni.com.whatshouldiwatch.data.NewsApi
import com.stameni.com.whatshouldiwatch.data.networkData.lists.FetchGenreListUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.lists.FetchGenreListUseCaseImpl
import com.stameni.com.whatshouldiwatch.data.networkData.lists.FetchListMoviesUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.lists.FetchListMoviesUseCaseImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.*
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast.FetchSingleMovieActors
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.cast.FetchSingleMovieActorsImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.certification.FetchSingleMovieCertification
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.certification.FetchSingleMovieCertificationImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.details.FetchSingleMovieDetails
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.details.FetchSingleMovieDetailsImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.images.FetchSingleMovieImages
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.images.FetchSingleMovieImagesImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendations
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.recommendations.FetchSingleMovieRecommendationsImpl
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.trailer.FetchSingleMovieTrailer
import com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.trailer.FetchSingleMovieTrailerImpl
import com.stameni.com.whatshouldiwatch.data.networkData.news.FetchEntertainmentNewsUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.news.FetchEntertainmentNewsUseCaseImpl
import com.stameni.com.whatshouldiwatch.data.networkData.person.actorMovies.FetchSingleActorMoviesUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.person.actorMovies.FetchSingleActorMoviesUseCaseImpl
import com.stameni.com.whatshouldiwatch.data.networkData.person.directorMovies.FetchSingleDirectorMovies
import com.stameni.com.whatshouldiwatch.data.networkData.person.directorMovies.FetchSingleDirectorMoviesImpl
import com.stameni.com.whatshouldiwatch.data.networkData.person.personDetails.FetchPersonDetailsUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.person.personDetails.FetchPersonDetailsUseCaseImpl
import com.stameni.com.whatshouldiwatch.data.networkData.search.SearchByTermUseCase
import com.stameni.com.whatshouldiwatch.data.networkData.search.SearchByTermUseCaseImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    internal fun getNewsApi(retrofit: Retrofit): NewsApi {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("apiKey", NEWS_API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(requestInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NEWS_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }


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
    fun getSearchByTermUseCase(movieApi: MovieApi): SearchByTermUseCase = SearchByTermUseCaseImpl(movieApi)

    @Provides
    fun getEntertainmentNewsUseCase(newsApi: NewsApi): FetchEntertainmentNewsUseCase = FetchEntertainmentNewsUseCaseImpl(newsApi)

    @Provides
    fun getSingleMovieImages(movieApi: MovieApi): FetchSingleMovieImages = FetchSingleMovieImagesImpl(movieApi)

    @Provides
    fun getSingleMovieActors(movieApi: MovieApi): FetchSingleMovieActors = FetchSingleMovieActorsImpl(movieApi)

    @Provides
    fun getSingleMovieRecommendations(movieApi: MovieApi): FetchSingleMovieRecommendations = FetchSingleMovieRecommendationsImpl(movieApi)

    @Provides
    fun getSingleMoviesDetails(movieApi: MovieApi): FetchSingleMovieDetails = FetchSingleMovieDetailsImpl(movieApi)

    @Provides
    fun getActorDetails(movieApi: MovieApi): FetchPersonDetailsUseCase = FetchPersonDetailsUseCaseImpl(movieApi)

    @Provides
    fun getActorMovies(movieApi: MovieApi): FetchSingleActorMoviesUseCase =
        FetchSingleActorMoviesUseCaseImpl(movieApi)

    @Provides
    fun getSingleMovieCerfitication(movieApi: MovieApi): FetchSingleMovieCertification = FetchSingleMovieCertificationImpl(movieApi)

    @Provides
    fun getSingleMovieTrailer(movieApi: MovieApi): FetchSingleMovieTrailer = FetchSingleMovieTrailerImpl(movieApi)

    @Provides
    fun getSingleDirectorMovies(movieApi: MovieApi): FetchSingleDirectorMovies = FetchSingleDirectorMoviesImpl(movieApi)
}
