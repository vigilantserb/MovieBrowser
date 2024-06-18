package com.stameni.com.moviebrowser.screens.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.DiscoverMoviesFragmentBinding

class DiscoverMoviesFragment : BaseFragment<DiscoverMoviesFragmentBinding>(DiscoverMoviesFragmentBinding::inflate) {

    private lateinit var viewModel: DiscoverMoviesViewModel
    private lateinit var viewpager_main: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.discover_movies_fragment, container, false)
    }

    override fun setupViews() {
        viewpager_main = binding.viewpagerMain
        tabs = binding.tabs
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
