package com.stameni.com.whatshouldiwatch.screens.mylist.toWatch

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_to_watch.*
import javax.inject.Inject

class ToWatchActivity : BaseActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_to_watch)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToWatchViewModel::class.java)
        val layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        val adapter = ToWatchListAdapter(
            ArrayList(),
            imageLoader,
            viewModel
        )

        viewModel.fetchListMovies("toWatch")

        viewModel.fetchedMovies.observe(this, Observer {
            adapter.addAll(it)
        })

        movies_rv.adapter = adapter
        movies_rv.layoutManager = layoutManager
    }
}
