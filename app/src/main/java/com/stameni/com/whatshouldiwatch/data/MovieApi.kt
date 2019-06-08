package com.stameni.com.whatshouldiwatch.data

import com.stameni.com.quizforall.common.ConnectivityInterceptor
import com.stameni.com.whatshouldiwatch.data.schemas.GenreListSchema
import com.stameni.com.whatshouldiwatch.data.schemas.GenreSchema
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val API_KEY = "5e35bda1500b7f696342a3ab91d79e52"
const val BASE_URL = "https://api.themoviedb.org/"

interface MovieApi {

    @GET("/3/genre/movie/list")
    fun getDbGenres(
    ): Observable<Response<GenreListSchema>>

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