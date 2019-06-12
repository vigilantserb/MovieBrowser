package com.stameni.com.whatshouldiwatch.screens.movielist

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.content_movie_list.*
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    private lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)

        if(intent.extras != null){
            val title = intent.extras!!.getString("title", "")
            val url = intent.extras!!.getString("url", "")
            val id = intent.extras!!.getString("id", "0")

            supportActionBar?.title = title

            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            viewModel.getListMovies(id.toString())
            viewModel.fetchedGenres.observe(this, Observer {
                if(it != null){
                    recycler_view.adapter = ListMoviesAdapter(it)
                }
            })

            Glide.with(this).load("https://image.tmdb.org/t/p/original/$url")
                .centerCrop()
                .into(header_image_view)
        }
    }
}