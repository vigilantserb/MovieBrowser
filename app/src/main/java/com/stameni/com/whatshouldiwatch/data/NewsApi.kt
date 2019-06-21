package com.stameni.com.whatshouldiwatch.data

import com.stameni.com.whatshouldiwatch.common.interceptors.ConnectivityInterceptor
import com.stameni.com.whatshouldiwatch.data.schemas.genre.GenreListSchema
import com.stameni.com.whatshouldiwatch.data.schemas.news.NewsSearchSchema
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val NEWS_BASE_URL = "https://newsapi.org/v2/"
const val NEWS_API_KEY = "825392855b5d4944afdc20a5782d174b"

interface NewsApi {

    @GET("/everything")
    fun getEntertainmentNews(
        @Query("q") terms: String = "premiere AND movie",
        @Query("language") language: String = "en"
    ): Observable<Response<NewsSearchSchema>>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): NewsApi {
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
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NEWS_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }
    }
}