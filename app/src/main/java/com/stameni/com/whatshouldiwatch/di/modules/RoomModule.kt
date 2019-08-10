package com.stameni.com.whatshouldiwatch.di.modules

import android.content.Context
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.data.room.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun getMovieDatabase(context: Context): MovieDatabase =
        MovieDatabase(context) as MovieDatabase

    @Singleton
    @Provides
    fun getMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.movieDao()
}