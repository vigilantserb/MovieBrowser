package com.stameni.com.whatshouldiwatch.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stameni.com.whatshouldiwatch.data.room.models.Movie
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {
    @Query("select * from movie")
    fun getMovies(): Observable<List<Movie>>

    @Insert
    fun insertMovie(movie: Movie): Completable
}