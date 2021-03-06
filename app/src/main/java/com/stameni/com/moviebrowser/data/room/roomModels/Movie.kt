package com.stameni.com.moviebrowser.data.room.roomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.stameni.com.moviebrowser.data.room.DataTypeConverter

@Entity(tableName = "movie")
@TypeConverters(DataTypeConverter::class)
class Movie(
    @ColumnInfo(name = "movieTitle")
    val movieTitle: String?,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String?,
    @ColumnInfo(name = "movieGenres")
    val movieGenres: String?,
    @ColumnInfo(name = "movieImageUrl")
    val movieImageUrl: String?,
    @ColumnInfo(name = "movieId")
    val movieId: Int?,
    @ColumnInfo(name = "listType")
    val listType: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}