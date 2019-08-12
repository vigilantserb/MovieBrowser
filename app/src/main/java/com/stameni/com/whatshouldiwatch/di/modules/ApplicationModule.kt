package com.stameni.com.whatshouldiwatch.di.modules

import android.app.Application
import android.content.Context
import com.stameni.com.whatshouldiwatch.common.interceptors.ConnectivityInterceptor
import com.stameni.com.whatshouldiwatch.common.interceptors.ConnectivityInterceptorImpl
import com.stameni.com.whatshouldiwatch.data.retrofit.BASE_URL
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.NewsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun getApplication(): Application = mApplication

    @Provides
    internal fun getApplicationContext(): Context = mApplication

    @Provides
    internal fun getConnectivityInterceptor(context: Context): ConnectivityInterceptor =
        ConnectivityInterceptorImpl(context)

    @Singleton
    @Provides
    internal fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    internal fun getMovieApi(connectivityInterceptor: ConnectivityInterceptor): MovieApi =
        MovieApi(connectivityInterceptor)

    @Singleton
    @Provides
    internal fun getNewsApi(connectivityInterceptor: ConnectivityInterceptor): NewsApi =
        NewsApi.invoke(connectivityInterceptor)

}