package com.stameni.com.whatshouldiwatch.data

import com.stameni.com.whatshouldiwatch.common.interceptors.ConnectivityInterceptor
import com.stameni.com.whatshouldiwatch.data.schemas.genre.GenreListSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.MovieListSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.SearchSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.cast.SingleMovieCastSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.certification.CertificationResults
import com.stameni.com.whatshouldiwatch.data.schemas.movie.details.SingleMovieDetailsSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.images.SingleMovieImagesSchema
import com.stameni.com.whatshouldiwatch.data.schemas.movie.trailer.SingleMovieVideosSchema
import com.stameni.com.whatshouldiwatch.data.schemas.person.PeopleSearchSchema
import com.stameni.com.whatshouldiwatch.data.schemas.person.singlePerson.SinglePersonSchema
import com.stameni.com.whatshouldiwatch.data.schemas.tvShow.TvShowSearchSchema
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "5e35bda1500b7f696342a3ab91d79e52"
const val BASE_URL = "https://api.themoviedb.org/"

interface MovieApi {

    @GET("/3/genre/movie/list")
    fun getDbGenres(
    ): Observable<Response<GenreListSchema>>

    @GET("/3/list/{list_id}")
    fun getListMovies(
        @Path("list_id") listId: String
    ): Observable<Response<MovieListSchema>>

    @GET("/3/discover/movie")
    fun getGenreMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ): Observable<Response<SearchSchema>>

    @GET("/3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): Observable<Response<SearchSchema>>

    @GET("/3/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): Observable<Response<SearchSchema>>

    @GET("/3/search/movie")
    fun searchMovies(
        @Query("query") query: String
    ): Observable<Response<SearchSchema>>

    @GET("/3/search/tv")
    fun searchTvShows(
        @Query("query") query: String
    ): Observable<Response<TvShowSearchSchema>>

    @GET("/3/search/person")
    fun searchPeople(
        @Query("query") query: String
    ): Observable<Response<PeopleSearchSchema>>

    @GET("/3/movie/{movie_id}/images")
    fun getSingleMovieImages(
        @Path("movie_id") movieId: Int
    ): Observable<Response<SingleMovieImagesSchema>>

    @GET("/3/movie/{movie_id}/credits")
    fun getSingleMovieActors(
        @Path("movie_id") movieId: Int
    ): Observable<Response<SingleMovieCastSchema>>

    @GET("/3/movie/{movie_id}/release_dates")
    fun getSingleMovieCertification(
        @Path("movie_id") movieId: Int
    ): Observable<Response<CertificationResults>>

    @GET("/3/movie/{movie_id}/videos")
    fun getSingleMovieTrailer(
        @Path("movie_id") movieId: Int
    ): Observable<Response<SingleMovieVideosSchema>>

    @GET("/3/movie/{movie_id}/recommendations")
    fun getSingleMovieRecommendations(
        @Path("movie_id") movieId: Int
    ): Observable<Response<SearchSchema>>

    @GET("/3/movie/{movie_id}")
    fun getSingleMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "credits"
    ): Observable<Response<SingleMovieDetailsSchema>>

    @GET("/3/person/{person_id}")
    fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("append_to_response") append: String = "combined_credits"
    ): Observable<Response<SinglePersonSchema>>

    @GET("/3/discover/movie")
    fun getSingleActorMovies(
        @Query("with_cast") castId: Int,
        @Query("page") page: Int = 1
    ): Observable<Response<SearchSchema>>

    @GET("/3/discover/movie")
    fun getSingleDirectorMovies(
        @Query("with_crew") crewId: Int,
        @Query("page") page: Int = 1
    ): Observable<Response<SearchSchema>>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): MovieApi {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
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
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }
    }
}