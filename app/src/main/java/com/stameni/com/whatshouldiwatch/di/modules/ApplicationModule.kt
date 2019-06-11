package com.stameni.com.whatshouldiwatch.di.modules

import android.app.Application
import com.stameni.com.whatshouldiwatch.data.BASE_URL
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

    @Singleton
    @Provides
    internal fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
