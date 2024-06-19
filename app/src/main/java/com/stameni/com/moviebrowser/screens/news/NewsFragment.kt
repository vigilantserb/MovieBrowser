package com.stameni.com.moviebrowser.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.common.libraries.CustomSnackbar
import com.stameni.com.moviebrowser.databinding.NewsFragmentBinding
import javax.inject.Inject

class NewsFragment : BaseFragment<NewsFragmentBinding>(NewsFragmentBinding::inflate) {

    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private var currentPage = 1
    private var totalPages = 0

    private lateinit var news_recycler_view: RecyclerView
    private lateinit var gif_progress_bar: ProgressBar

    override fun setupViews() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        val adapter = NewsAdapter(imageLoader)

        news_recycler_view.adapter = adapter
        news_recycler_view.layoutManager = LinearLayoutManager(view.context)
        news_recycler_view.isNestedScrollingEnabled = false

        var snackbar = CustomSnackbar.make(view)
        snackbar.duration = 2000

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)

        viewModel.fetchEntertainmentNews(1)

        news_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if(currentPage <= totalPages){
                        currentPage++
                        viewModel.fetchEntertainmentNews(currentPage)
                        if (!snackbar.isShown) {
                            snackbar.show()
                        }
                    }
                }
            }
        })

        viewModel.fetchedNews.observe(this, Observer {
            if (it != null) {
                if(gif_progress_bar.visibility == View.VISIBLE) gif_progress_bar.visibility = View.GONE
                adapter.addAll(it)
            }
        })

        viewModel.totalPages.observe(this, Observer {
            if(it != null){
                totalPages = it
            }
        })
    }
}
