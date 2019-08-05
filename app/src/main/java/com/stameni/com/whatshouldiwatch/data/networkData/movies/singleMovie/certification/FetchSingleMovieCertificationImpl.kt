package com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.certification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.schemas.movie.certification.CertificationResults
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieCertificationImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieCertification {

    private val _fetchedCertification = MutableLiveData<String>()
    override val fetchedCertification: LiveData<String>
        get() = _fetchedCertification


    private val _fetchError = MutableLiveData<Exception>()
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getSingleMovieCertification(movieId: Int): Disposable {
        return movieApi.getSingleMovieCertification(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onActorsFetched(it) }, { onActorsFetchFailed(it) })
    }

    private fun onActorsFetchFailed(it: Throwable?) {
        _fetchError.value = it as Exception
    }

    private fun onActorsFetched(response: Response<CertificationResults>) {
       if(response.isSuccessful){
           var result = ""
           response.body()!!.results.forEach {
               if(it.country == "US")
               it.certificationList.forEach {
                   result = it.certification
               }
           }
           _fetchedCertification.value = result
       }
    }
}