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
import com.stameni.com.whatshouldiwatch.data.models.Genre
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GenreMoviesViewModel::class.java)
        val adapter = GenreListAdapter(ArrayList())
        movie_recycler_view.adapter = adapter

        viewModel.getGenreList()

        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager = LinearLayoutManager(context)

        viewModel.fetchedGenres.observe(this, Observer {
            movie_recycler_view.visibility = View.VISIBLE
            gif_progress_bar.visibility = View.GONE
            adapter.addAll(it)
        })

        viewModel.fetchError.observe(this, Observer {
            movie_recycler_view.visibility = View.VISIBLE
            gif_progress_bar.visibility = View.GONE
            Log.e("test", Log.getStackTraceString(it))
        })
    }
}
