package com.stameni.com.whatshouldiwatch.screens.mylist.watched

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_watched.*
import javax.inject.Inject

class WatchedActivity : BaseActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_watched)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(WatchedViewModel::class.java)
        val layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        val adapter = WatchedListAdapter(
            ArrayList(),
            imageLoader,
            viewModel
        )

        viewModel.fetchListMovies("watched")

        viewModel.fetchedMovies.observe(this, Observer {
            adapter.addAll(it)
        })

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        movies_rv.adapter = adapter
        movies_rv.layoutManager = layoutManager
    }
}
