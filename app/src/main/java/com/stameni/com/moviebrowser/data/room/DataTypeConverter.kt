package com.stameni.com.moviebrowser.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import java.util.Collections.emptyList


object DataTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<Movie> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Movie>>() {

        }.type

        return gson.fromJson<List<Movie>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<Movie>): String {
        return gson.toJson(someObjects)
    }
}