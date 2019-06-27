package com.stameni.com.whatshouldiwatch.data.networkData.news

import androidx.lifecycle.LiveData
import com.stameni.com.whatshouldiwatch.data.models.NewsItem
import io.reactivex.disposables.Disposable

interface FetchEntertainmentNewsUseCase {

    val fetchedNews: LiveData<ArrayList<NewsItem>>

    val totalPages: LiveData<Int>

    val fetchError: LiveData<Exception>

    fun fetchEntertainmentNews(currentPage: Int): Disposable
}