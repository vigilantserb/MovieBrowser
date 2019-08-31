package com.stameni.com.moviebrowser.screens.mylist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.screens.mylist.toWatch.ToWatchActivity
import com.stameni.com.moviebrowser.screens.mylist.watched.WatchedActivity
import kotlinx.android.synthetic.main.my_list_fragment.*
import javax.inject.Inject

class MyListFragment : BaseFragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyListViewModel::class.java)

        viewModel.countMoviesByType()

        to_watch_placeholder.setOnClickListener {
            startActivity(Intent(context, ToWatchActivity::class.java))
        }
        watched_placeholder.setOnClickListener {
            startActivity(Intent(context, WatchedActivity::class.java))
        }

        viewModel.toWatchCount.observe(this, Observer {
            to_watch_count.text = it.toString()
        })

        viewModel.watchedCount.observe(this, Observer {
            watched_count.text = it.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.countMoviesByType()
    }
}
