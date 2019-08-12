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
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.common.libraries.CustomSnackbar
import com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist.MovieGridAdapter
import kotlinx.android.synthetic.main.upcoming_movies_fragment.*
import javax.inject.Inject

class UpcomingMovies : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: UpcomingMoviesViewModel

    private var currentPage = 1

    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upcoming_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        currentPage = 1

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(view.context, 3, RecyclerView.VERTICAL, false)
        val adapter = MovieGridAdapter(ArrayList(), imageLoader)

        movie_recycler_view.layoutManager = gridLayoutManager
        movie_recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        viewModel.getUpcomingMovies(1)

        var snackbar = CustomSnackbar.make(view)
        snackbar.duration = 1000

        movie_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1) {
                    if(currentPage <= totalPages){
                        currentPage++
                        viewModel.getUpcomingMovies(currentPage)
                        if (!snackbar.isShown) {
                            snackbar.show()
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        viewModel.totalPages.observe(this, Observer {
            if (it != null)
                totalPages = it
        })

        viewModel.fetchedMovies.observe(this, Observer {
            if (it != null) {
                if(gif_progress_bar.visibility == View.VISIBLE) gif_progress_bar.visibility = View.GONE
                adapter.addAll(it)
            }
        })

        viewModel.fetchError.observe(this, Observer {
            Log.e("Error", Log.getStackTraceString(it))
        })
    }
}
