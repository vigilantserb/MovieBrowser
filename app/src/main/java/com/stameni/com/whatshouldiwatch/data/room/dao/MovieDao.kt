package com.stameni.com.whatshouldiwatch.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("select * from movie")
    fun getMovies(): Single<List<Movie>>

    @Insert
    fun insertMovie(movie: Movie): Completable

    @Insert
    fun insertMovieList(movies: List<Movie>): Completable

    @Delete
    fun deleteMovie(movie: Movie): Completable

    @Query("UPDATE movie SET listType=:listType WHERE movieId = :id")
    fun updateMovie(listType: String, id: Int?): Completable
}