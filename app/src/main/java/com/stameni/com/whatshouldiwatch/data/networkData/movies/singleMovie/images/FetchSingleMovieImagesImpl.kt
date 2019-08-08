package com.stameni.com.whatshouldiwatch.data.networkData.movies.singleMovie.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieImage
import com.stameni.com.whatshouldiwatch.data.schemas.movie.images.SingleMovieImagesSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchSingleMovieImagesImpl(
    private val movieApi: MovieApi
) : FetchSingleMovieImages {
    
    private val _fetchedImages = MutableLiveData<ArrayList<MovieImage>>()
    
    override val fetchedImages: LiveData<ArrayList<MovieImage>>
        get() = _fetchedImages
    
    private val _fetchError = MutableLiveData<java.lang.Exception>()
    
    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun getSingleMovieImages(movieId: Int): Disposable {
        return movieApi.getSingleMovieImages(movieId)
            .subscribeOn(Schedulers.io())
            .map {
                formatResponseData(it)
            }
            .map {
                sortImagesByRating(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onImagesFetched(it) }, { onImagesFetchFailed(it) })
    }

    private fun onImagesFetchFailed(it: Throwable?) {
        _fetchError.value = it as java.lang.Exception
    }

    private fun onImagesFetched(response: ArrayList<MovieImage>?) {
        _fetchedImages.value = response
    }

    private fun sortImagesByRating(response: ArrayList<MovieImage>): ArrayList<MovieImage> {
        var images = response.toList()

        images = images.sortedWith(compareByDescending { it.imageRating })

        return ArrayList(images)
    }

    private fun formatResponseData(response: Response<SingleMovieImagesSchema>): ArrayList<MovieImage> {
        val formattedData = ArrayList<MovieImage>()

        if(response.isSuccessful){
            if(response.body()?.backdrops != null){
                response.body()!!.backdrops.forEach {
                    formattedData.add(
                        MovieImage(
                            it.filePath,
                            it.voteAverage
                        )
                    )
                }
            }
        }

        return formattedData
    }
}