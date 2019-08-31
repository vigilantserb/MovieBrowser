package com.stameni.com.moviebrowser.data.retrofit

import com.stameni.com.moviebrowser.common.interceptors.ConnectivityInterceptor
import com.stameni.com.moviebrowser.data.TmdbException
import com.stameni.com.moviebrowser.data.retrofit.schemas.genre.GenreListSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.MovieListSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.SearchSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.SingleMovieDetailsSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.person.PeopleSearchSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.person.singlePerson.SinglePersonSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.tvShow.TvShowSearchSchema
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
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

    @GET("/3/movie/{movie_id}")
    fun getSingleMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "images,credits,release_dates,videos,recommendations"
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

            val errorInterceptor = Interceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                if (response.code() > 201) {
                    val jObjError = JSONObject(response.body()!!.string())
                    val x = jObjError.getString("status_message")
                    val y = jObjError.getString("status_code")
                    throw TmdbException("Server message: $x     HTTP code: ${response.code()}. Status code:$y")
                }
                response
            }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(errorInterceptor)
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