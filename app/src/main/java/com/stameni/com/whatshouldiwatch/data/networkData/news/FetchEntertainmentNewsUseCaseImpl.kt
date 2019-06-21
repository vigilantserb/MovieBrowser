package com.stameni.com.whatshouldiwatch.data.networkData.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.whatshouldiwatch.data.NewsApi
import com.stameni.com.whatshouldiwatch.data.models.NewsItem
import com.stameni.com.whatshouldiwatch.data.schemas.news.NewsSearchSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchEntertainmentNewsUseCaseImpl(
    private val newsApi: NewsApi
) : FetchEntertainmentNewsUseCase {

    private val _fetchedNews = MutableLiveData<ArrayList<NewsItem>>()

    private val _fetchError = MutableLiveData<java.lang.Exception>()

    override val fetchedNews: LiveData<ArrayList<NewsItem>>
        get() = _fetchedNews

    override val fetchError: LiveData<Exception>
        get() = _fetchError

    override fun fetchEntertainmentNews(): Disposable {
        return newsApi.getEntertainmentNews()
            .subscribeOn(Schedulers.io())
            .map { formatResponseData(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onNewsFetch(it) }, { onNewsFetchFail(it as java.lang.Exception) })
    }

    private fun onNewsFetchFail(exception: java.lang.Exception) {
        _fetchError.value = exception
    }

    private fun onNewsFetch(response: ArrayList<NewsItem>) {
        _fetchedNews.value = response
    }

    private fun formatResponseData(response: Response<NewsSearchSchema>): ArrayList<NewsItem> {
        val newsData = ArrayList<NewsItem>()

        if(response.body() != null){
            response.body()!!.articles.forEach {
                newsData.add(NewsItem(it.title, it.description, it.publishedAt, it.source.name, it.urlToImage))
            }
        }

        return newsData
    }
}