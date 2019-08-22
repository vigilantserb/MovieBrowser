package com.stameni.com.whatshouldiwatch.data.room.localData

import android.Manifest
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.opencsv.CSVReader
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.data.room.roomModels.Movie
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.RequestPermissionUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.FileReader

class ImportMovieListFromCsvUseCase(
    private val movieDatabase: MovieDatabase,
    private val requestPermission: RequestPermissionUseCase
) {

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String>
        get() = _successMessage

    init {
        requestPermission.permissionApproved.observeForever {
            if (it == Constants.SUCCESS) {
                getAllMovies()
            }
        }
    }

    fun requestCsvFileRead() {
        val permissionsArray = ArrayList<String>()
        permissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermission.requestPermissions(permissionsArray, "Permission denied - CSV file not read")
    }

    private fun getAllMovies(): Disposable {
        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fetchSuccessful(it) }, { fetchFail(it) })
    }

    private fun fetchFail(it: Throwable?) {
        println("Fetch failed.")
    }

    private fun fetchSuccessful(movieListFromLocalDb: List<Movie>?) {
        val movieListFromCsv = getMovieListFromCsvFile()
        val combinedMovieList = movieListFromLocalDb!! + movieListFromCsv
        val finalMovieList = combinedMovieList.groupBy { it.movieId }
            .filter { it.value.size == 1 }
            .flatMap { it.value }
        addMoviesToLocalDatabase(finalMovieList)
    }

    private fun addMoviesToLocalDatabase(finalMovieList: List<Movie>): Disposable {
        return movieDatabase.movieDao()
            .insertMovieList(finalMovieList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _successMessage.value = "Movies from CSV file added to local database."
            }, { e -> Timber.d(e) })
    }

    private fun getMovieListFromCsvFile(): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()
        try {
            val reader =
                FileReader(Environment.getExternalStorageDirectory().toString() + "/" + "Movies.csv")
            val csvReader = CSVReader(reader)
            var nextRecord = csvReader.readNext()
            while (nextRecord != null) {
                if (!nextRecord[0].contains("movieTitle"))
                    movieList.add(
                        Movie(
                            nextRecord[0],
                            nextRecord[1],
                            nextRecord[2],
                            nextRecord[3],
                            nextRecord[4].toInt(),
                            nextRecord[5]
                        )
                    )
                nextRecord = csvReader.readNext()
            }
            return movieList
        } catch (e: java.lang.Exception) {
            println("An error occured while reading the CSV file.")
        }
        return movieList
    }
}