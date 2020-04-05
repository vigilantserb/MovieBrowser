package com.stameni.com.moviebrowser.data.retrofit.networkData.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.data.models.SearchItem
import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.SearchSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.person.PeopleSearchSchema
import com.stameni.com.moviebrowser.data.retrofit.schemas.tvShow.TvShowSearchSchema
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

    val _fetchError = MutableLiveData<String>()

    override val fetchError: LiveData<String>
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
        _fetchError.value = exception.localizedMessage
    }

    private fun onSearchDataFetch(response: List<SearchItem>) {
        _fetchedData.value = ArrayList(response)
    }

    private fun formatSearchData(movieData: Response<SearchSchema>, peopleData: Response<PeopleSearchSchema>, tvShowData: Response<TvShowSearchSchema>): ArrayList<SearchItem> {
        val searchData = ArrayList<SearchItem>()

        tvShowData.body()?.results?.let { tvData ->
            tvData.forEach {
                searchData.add(SearchItem(it.name, it.posterPath, Constants.TV_SHOW_TYPE, it.firstAirDate, it.id))
            }
        }

        movieData.body()?.results?.let { movieData ->
            movieData.forEach {
                searchData.add(SearchItem(it.title, it.posterPath, Constants.MOVIE_TYPE, it.releaseDate, it.id))
            }
        }

        peopleData.body()?.results?.let { peopleData ->
            peopleData.forEach {
                searchData.add(SearchItem(it.name, it.profilePath, Constants.PEOPLE_TYPE, "", it.id))
            }
        }

        return searchData
    }
}