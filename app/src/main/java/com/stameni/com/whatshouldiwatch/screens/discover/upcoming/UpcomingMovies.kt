package com.stameni.com.whatshouldiwatch.screens.discover.upcoming

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class UpcomingMovies : Fragment() {

    companion object {
        fun newInstance() = UpcomingMovies()
    }

    private lateinit var viewModel: UpcomingMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upcoming_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UpcomingMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
