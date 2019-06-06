package com.stameni.com.whatshouldiwatch.screens.discover.nowPlaying

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class NowPlayingMovies : Fragment() {

    companion object {
        fun newInstance() = NowPlayingMovies()
    }

    private lateinit var viewModel: NowPlayingMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.now_playing_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NowPlayingMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
