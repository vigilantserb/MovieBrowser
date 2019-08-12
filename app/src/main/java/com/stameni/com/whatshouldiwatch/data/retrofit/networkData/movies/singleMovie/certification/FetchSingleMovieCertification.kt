package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.certification

import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable

interface FetchSingleMovieCertification {

    val fetchedCertification: LiveData<String>

    val fetchError: LiveData<Exception>

    fun getSingleMovieCertification(movieId: Int): Disposable
}