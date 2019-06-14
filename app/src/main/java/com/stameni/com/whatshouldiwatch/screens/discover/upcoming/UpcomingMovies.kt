package com.stameni.com.whatshouldiwatch.screens.discover.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist.MovieGridAdapter
import kotlinx.android.synthetic.main.upcoming_movies_fragment.*
import javax.inject.Inject

class UpcomingMovies : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UpcomingMoviesViewModel

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upcoming_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(view!!.context, 3, RecyclerView.VERTICAL, false)
        val adapter = MovieGridAdapter(ArrayList())

        recycler_view.layoutManager = gridLayoutManager
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        viewModel.getUpcomingMovies(1)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1) {
                    currentPage++
                    viewModel.getUpcomingMovies(currentPage)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        viewModel.fetchedMovies.observe(this, Observer {
            if (it != null) {
                adapter.addAll(it)
            }
        })

        viewModel.fetchError.observe(this, Observer {
            Log.e("Error", Log.getStackTraceString(it))
        })
    }
}
