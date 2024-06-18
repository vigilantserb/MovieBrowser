package com.stameni.com.moviebrowser.screens.discover.topLists.movielist

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pollux.widget.DualProgressView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.databinding.ActivityMovieListBinding
import javax.inject.Inject

class MovieListActivity :
    BaseActivity<ActivityMovieListBinding>(ActivityMovieListBinding::inflate) {

    private lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var toolbar: Toolbar
    private lateinit var movie_recycler_view: RecyclerView
    private lateinit var progress_bar: DualProgressView
    private lateinit var header_image_view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        var adapter = ListMoviesAdapter(ArrayList(), imageLoader)

        movie_recycler_view.adapter = adapter
        movie_recycler_view.layoutManager = layoutManager

        if (intent.extras != null) {
            val title = intent.extras!!.getString("title", "")
            val url = intent.extras!!.getString("url", "")
            val id = intent.extras!!.getString("id", "0")

            supportActionBar?.title = title
            viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            viewModel.getListMovies(id.toString())
            viewModel.fetchedGenres.observe(this, Observer {
                if (it != null) {
                    progress_bar.visibility = View.GONE
                    adapter.addAll(it)
                }
            })

            viewModel.fetchError.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })

            imageLoader.loadPosterImageCenterCrop(url, header_image_view, "w500")
        }
    }

    override fun setupViews() {
        toolbar = binding.toolbar
        movie_recycler_view = binding.contentMovieList.movieRecyclerView
        progress_bar = binding.contentMovieList.progressBar
        header_image_view = binding.headerImageView
    }
}