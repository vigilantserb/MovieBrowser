package com.stameni.com.whatshouldiwatch.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stameni.com.whatshouldiwatch.data.room.dao.MovieDao
import com.stameni.com.whatshouldiwatch.data.room.models.Movie


@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: RoomDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): RoomDatabase {
            return Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie.db").build()
        }
    }
}