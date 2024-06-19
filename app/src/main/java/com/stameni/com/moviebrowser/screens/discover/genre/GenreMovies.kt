package com.stameni.com.moviebrowser.screens.discover.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.GenreMoviesFragmentBinding
import javax.inject.Inject

class GenreMovies : BaseFragment<GenreMoviesFragmentBinding>(GenreMoviesFragmentBinding::inflate) {

    private lateinit var viewModel: GenreMoviesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var gifProgressBar: ImageView

    override fun setupViews() {
        movieRecyclerView = binding.movieRecyclerView
        gifProgressBar = binding.gifProgressBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(GenreMoviesViewModel::class.java)
        val adapter = GenreListAdapter(imageLoader)
        movieRecyclerView.adapter = adapter

        viewModel.getGenreList()

        movieRecyclerView.setHasFixedSize(true)
        movieRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.fetchedGenres.observe(this, Observer {
            movieRecyclerView.visibility = View.VISIBLE
            gifProgressBar.visibility = View.GONE
            adapter.addAll(it)
        })

        viewModel.fetchError.observe(this, Observer {
            movieRecyclerView.visibility = View.VISIBLE
            gifProgressBar.visibility = View.GONE
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}
