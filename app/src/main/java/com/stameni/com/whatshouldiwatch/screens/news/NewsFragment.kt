package com.stameni.com.whatshouldiwatch.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.CustomSnackbar
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment : BaseFragment() {
    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var currentPage = 1
    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        val adapter = NewsAdapter(ArrayList())

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
