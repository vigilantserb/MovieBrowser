package com.stameni.com.whatshouldiwatch.screens.discover.genre

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class GenreMovies : Fragment() {

    companion object {
        fun newInstance() = GenreMovies()
    }

    private lateinit var viewModel: GenreMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.genre_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GenreMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
