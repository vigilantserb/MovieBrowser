package com.stameni.com.moviebrowser.screens.news

import androidx.lifecycle.ViewModel
import com.stameni.com.moviebrowser.data.retrofit.networkData.news.FetchEntertainmentNewsUseCase
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel(
    private val fetchEntertainmentNewsUseCase: FetchEntertainmentNewsUseCase
) : ViewModel() {

    val disposables = CompositeDisposable()

    val fetchedNews
        get() = fetchEntertainmentNewsUseCase.fetchedNews

    val totalPages
        get() = fetchEntertainmentNewsUseCase.totalPages

    fun fetchEntertainmentNews(currentPage: Int) {
        disposables.add(
            fetchEntertainmentNewsUseCase.fetchEntertainmentNews(currentPage)
        )
    }
}
