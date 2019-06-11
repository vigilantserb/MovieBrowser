package com.stameni.com.whatshouldiwatch.screens.discover.genre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import kotlinx.android.synthetic.main.genre_movies_fragment.*
import javax.inject.Inject

class GenreMovies : BaseFragment() {

    private lateinit var viewModel: GenreMoviesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.genre_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GenreMoviesViewModel::class.java)

        viewModel.getGenreList()

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.fetchedGenres.observe(this, Observer {
            recycler_view.visibility = View.VISIBLE
            gif_progress_bar.visibility = View.GONE
            recycler_view.adapter = GenreListAdapter(it)
        })

        viewModel.fetchError.observe(this, Observer {
            recycler_view.visibility = View.VISIBLE
            gif_progress_bar.visibility = View.GONE
            Log.e("test", Log.getStackTraceString(it))
        })

    }

}
