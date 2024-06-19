package com.stameni.com.moviebrowser.screens.discover.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pollux.widget.DualProgressView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.common.libraries.CustomSnackbar
import com.stameni.com.moviebrowser.databinding.UpcomingMoviesFragmentBinding
import com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist.MovieGridAdapter
import javax.inject.Inject

class UpcomingMovies :
    BaseFragment<UpcomingMoviesFragmentBinding>(UpcomingMoviesFragmentBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: UpcomingMoviesViewModel

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var gifProgressBar: DualProgressView

    private var currentPage = 1

    private var totalPages = 0

    override fun setupViews() {
        movieRecyclerView = binding.movieRecyclerView
        gifProgressBar = binding.gifProgressBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        currentPage = 1

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(view.context, 3, RecyclerView.VERTICAL, false)
        val adapter = MovieGridAdapter(imageLoader)

        movieRecyclerView.layoutManager = gridLayoutManager
        movieRecyclerView.adapter = adapter

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel::class.java)

        viewModel.getUpcomingMovies(1)

        var snackbar = CustomSnackbar.make(view)
        snackbar.duration = 1000

        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1) {
                    if (currentPage <= totalPages) {
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
                if (gifProgressBar.visibility == View.VISIBLE) gifProgressBar.visibility =
                    View.GONE
                adapter.addAll(it)
            }
        })

        viewModel.fetchError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}
