package com.stameni.com.whatshouldiwatch.data.networkdata

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.models.Genre
import com.stameni.com.whatshouldiwatch.data.schemas.GenreListSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class FetchGenreListUseCaseImpl @Inject constructor(
    private val movieApi: MovieApi
) : FetchGenreListUseCase {

    private val _genreListLiveData: MutableLiveData<List<Genre>> = MutableLiveData()

    override val genreListLiveData: LiveData<List<Genre>>
        get() = _genreListLiveData

    @SuppressLint("CheckResult")
    override fun getGenreList(): Disposable {
        return movieApi
            .getDbGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    onGenreListFetch(result)
                }, { onGenreListFetchFail(it) }
            )
    }

    private fun onGenreListFetch(result: Response<GenreListSchema>) {
        val genreList = ArrayList<Genre>()
        if (result.body() != null) {
            result.body()!!.genres.forEach {
                genreList.add(Genre(it.name, it.id))
            }
        }
        _genreListLiveData.value = genreList
    }

    private fun onGenreListFetchFail(it: Throwable) {
        throw it
    }
}