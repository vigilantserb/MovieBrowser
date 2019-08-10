package com.stameni.com.whatshouldiwatch.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class Movie (
    @ColumnInfo(name = "movieName")
    val movieName: String,
    @ColumnInfo(name = "movieGenres")
    val movieGenres: String,
    @ColumnInfo(name = "movieImageUrl")
    val movieImageUrl: String
){
    @PrimaryKey(autoGenerate = true)
    var movieId: Int = 0
}