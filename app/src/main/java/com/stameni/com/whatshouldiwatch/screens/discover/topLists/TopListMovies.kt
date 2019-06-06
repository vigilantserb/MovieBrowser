package com.stameni.com.whatshouldiwatch.screens.discover.topLists

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class TopListMovies : Fragment() {

    companion object {
        fun newInstance() = TopListMovies()
    }

    private lateinit var viewModel: TopListMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_list_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopListMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
