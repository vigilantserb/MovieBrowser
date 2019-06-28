package com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
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

        var layoutManager = LinearLayoutManager(this)
        var adapter = ListMoviesAdapter(ArrayList(), imageLoader)
        layoutManager.apply { isAutoMeasureEnabled = false }

        movie_recycler_view.adapter = adapter
        movie_recycler_view.layoutManager = layoutManager

            if(intent.extras != null){
            val title = intent.extras!!.getString("title", "")
            val url = intent.extras!!.getString("url", "")
            val id = intent.extras!!.getString("id", "0")

            supportActionBar?.title = title
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            viewModel.getListMovies(id.toString())
            viewModel.fetchedGenres.observe(this, Observer {
                if(it != null){
                    adapter.addAll(it)
                }
            })

            Glide.with(this).load("https://image.tmdb.org/t/p/original/$url")
                .centerCrop()
                .into(header_image_view)
        }
    }
}