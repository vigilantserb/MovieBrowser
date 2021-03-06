package com.stameni.com.moviebrowser.di.modules

import android.content.Context
import com.stameni.com.moviebrowser.data.room.MovieDatabase
import com.stameni.com.moviebrowser.data.room.dao.MovieDao
import com.stameni.com.moviebrowser.data.room.localData.*
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

    @Provides
    fun deleteSingleMovie(movieDatabase: MovieDatabase): DeleteMovieUseCase = DeleteMovieUseCase(movieDatabase)

    @Provides
    fun fetchMovieList(movieDatabase: MovieDatabase): FetchMovieListUseCase = FetchMovieListUseCase(movieDatabase)

    @Provides
    fun countMoviesByType(movieDatabase: MovieDatabase): CountMoviesByTypeUseCase =
        CountMoviesByTypeUseCase(movieDatabase)

    @Provides
    fun saveMovieToDb(movieDatabase: MovieDatabase): SaveMovieToDatabase = SaveMovieToDatabase(movieDatabase)

    @Provides
    fun updateMovieData(movieDatabase: MovieDatabase): UpdateMovieDataUseCase = UpdateMovieDataUseCase(movieDatabase)
}