package com.stameni.com.moviebrowser.screens.settings.useCases

import android.Manifest
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.opencsv.CSVWriter
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.data.room.MovieDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CreateCsvFileUseCase(
    private val movieDatabase: MovieDatabase,
    private val requestPermission: RequestPermissionUseCase
) {
    init {
        requestPermission.permissionApproved.observeForever {
            if (it == Constants.SUCCESS) {
                createCsvFile()
            }
        }
    }

    private val _writeSuccessful = MutableLiveData<Int>()
    val writeSuccessful: LiveData<Int>
        get() = _writeSuccessful

    fun requestCsvFileWrite() {
        val permissionsArray = ArrayList<String>()
        permissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermission.requestPermissions(permissionsArray, "Permission denied - CSV file not created")
    }

    private fun createCsvFile(): Disposable {
        val exportDir = File(Environment.getExternalStorageDirectory().path)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val file = File(exportDir, "Movies.csv")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
        val csvWrite = CSVWriter(FileWriter(file))
        val column = arrayOf("movieTitle", "releaseDate", "movieGenres", "movieImageUrl", "movieId", "listType")
        csvWrite.writeNext(column)

        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                for (i in movies.indices) {
                    val mySecondStringArray =
                        arrayOf(
                            movies[i].movieTitle!!,
                            movies[i].releaseDate!!,
                            movies[i].movieGenres!!,
                            movies[i].movieImageUrl!!,
                            movies[i].movieId.toString(),
                            movies[i].listType!!
                        )
                    csvWrite.writeNext(mySecondStringArray)
                }
                csvWrite.close()
                _writeSuccessful.value = Constants.SUCCESS
            }, {
                Timber.e(it)
            })
    }
}