package com.stameni.com.moviebrowser.screens.mylist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.MyListFragmentBinding
import com.stameni.com.moviebrowser.screens.mylist.toWatch.ToWatchActivity
import com.stameni.com.moviebrowser.screens.mylist.watched.WatchedActivity
import javax.inject.Inject

class MyListFragment : BaseFragment<MyListFragmentBinding>(MyListFragmentBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MyListViewModel

    private lateinit var toWatchPlaceholder: TextView
    private lateinit var watchedPlaceholder: TextView
    private lateinit var toWatchCount: TextView
    private lateinit var watchedCount: TextView

    override fun setupViews() {
        toWatchPlaceholder = binding.toWatchPlaceholder
        watchedPlaceholder = binding.watchedPlaceholder
        toWatchCount = binding.toWatchCount
        watchedCount = binding.watchedCount
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyListViewModel::class.java)

        viewModel.countMoviesByType()

        toWatchPlaceholder.setOnClickListener {
            startActivity(Intent(context, ToWatchActivity::class.java))
        }
        watchedPlaceholder.setOnClickListener {
            startActivity(Intent(context, WatchedActivity::class.java))
        }

        viewModel.toWatchCount.observe(this, Observer {
            toWatchCount.text = it.toString()
        })

        viewModel.watchedCount.observe(this, Observer {
            watchedCount.text = it.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.countMoviesByType()
    }
}
