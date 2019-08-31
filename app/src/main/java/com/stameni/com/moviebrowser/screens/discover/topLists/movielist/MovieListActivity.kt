package com.stameni.com.moviebrowser.screens.discover.topLists.movielist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.content_movie_list.*
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    private lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_movie_list)
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
}