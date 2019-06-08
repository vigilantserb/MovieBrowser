package com.stameni.com.whatshouldiwatch.di.modules

import android.content.Context
import com.stameni.com.quizforall.common.ConnectivityInterceptor
import com.stameni.com.quizforall.common.ConnectivityInterceptorImpl
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.networkdata.FetchGenreListUseCase
import com.stameni.com.whatshouldiwatch.data.networkdata.FetchGenreListUseCaseImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun getConnectivityInterceptor(context: Context): ConnectivityInterceptor =
        ConnectivityInterceptorImpl(context)

    @Singleton
    @Provides
    internal fun getMovieApi(connectivityInterceptor: ConnectivityInterceptor): MovieApi {
        return MovieApi.invoke(connectivityInterceptor)
    }

    @Provides
    fun getFetchGenresUseCase(movieApi: MovieApi): FetchGenreListUseCase {
        return FetchGenreListUseCaseImpl(movieApi)
    }
}
