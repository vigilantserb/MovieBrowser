package com.stameni.com.moviebrowser.data.retrofit.networkData.news

import androidx.lifecycle.LiveData
import com.stameni.com.moviebrowser.data.models.NewsItem
import io.reactivex.disposables.Disposable

interface FetchEntertainmentNewsUseCase {

    val fetchedNews: LiveData<ArrayList<NewsItem>>

    val totalPages: LiveData<Int>

    val fetchError: LiveData<Exception>

    fun fetchEntertainmentNews(currentPage: Int): Disposable
}