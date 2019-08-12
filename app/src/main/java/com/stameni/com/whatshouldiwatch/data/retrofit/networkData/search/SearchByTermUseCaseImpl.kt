package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import com.stameni.com.whatshouldiwatch.data.retrofit.MovieApi
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.SearchSchema
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.person.PeopleSearchSchema
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.tvShow.TvShowSearchSchema
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SearchByTermUseCaseImpl(
    private val movieApi: MovieApi
) : SearchByTermUseCase {

    val _fetchedData = MutableLiveData<ArrayList<SearchItem>>()

    override val fetchedData: LiveData<ArrayList<SearchItem>>
        get() = _fetchedData

    val _fetchError = MutableLiveData<java.lang.Exception>()

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun searchByTerm(term: String): Disposable {
        var movieData = movieApi.searchMovies(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        var peopleData = movieApi.searchPeople(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        var tvShowData = movieApi.searchTvShows(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return Observable.zip(
            movieData,
            peopleData,
            tvShowData,
            Function3<Response<SearchSchema>, Response<PeopleSearchSchema>, Response<TvShowSearchSchema>, ArrayList<SearchItem>> { t1, t2, t3 ->
                formatSearchData(t1, t2, t3)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onSearchDataFetch(it) }, { onSearchDataFetchFail(it as Exception) })
    }

    private fun onSearchDataFetchFail(exception: Exception) {
        _fetchError.value = exception
    }

    private fun onSearchDataFetch(response: List<SearchItem>) {
        _fetchedData.value = ArrayList(response)
    }

    private fun formatSearchData(movieData: Response<SearchSchema>?, peopleData: Response<PeopleSearchSchema>, tvShowData: Response<TvShowSearchSchema>): ArrayList<SearchItem> {
        val searchData = ArrayList<SearchItem>()

        if(tvShowData.body() != null){
            tvShowData.body()!!.results.forEach {
                searchData.add(SearchItem(it.name, it.posterPath, Constants.TV_SHOW_TYPE, it.firstAirDate, it.id))
            }
        }

        if(movieData?.body() != null){
            movieData.body()!!.results.forEach {
                searchData.add(SearchItem(it.title, it.posterPath, Constants.MOVIE_TYPE, it.releaseDate, it.id))
            }
        }

        if(peopleData.body() != null){
            peopleData.body()!!.results.forEach {
                searchData.add(SearchItem(it.name, it.profilePath, Constants.PEOPLE_TYPE, "", it.id))
            }
        }

        return searchData
    }
}