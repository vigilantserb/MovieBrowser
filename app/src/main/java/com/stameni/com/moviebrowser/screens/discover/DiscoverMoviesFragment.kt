package com.stameni.com.moviebrowser.screens.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.moviebrowser.R
import kotlinx.android.synthetic.main.discover_movies_fragment.*

class DiscoverMoviesFragment : Fragment() {

    private lateinit var viewModel: DiscoverMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.discover_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager_main.adapter = MovieTypeAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewpager_main)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiscoverMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
