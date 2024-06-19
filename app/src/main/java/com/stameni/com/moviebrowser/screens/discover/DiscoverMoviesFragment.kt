package com.stameni.com.moviebrowser.screens.discover

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.DiscoverMoviesFragmentBinding

class DiscoverMoviesFragment : BaseFragment<DiscoverMoviesFragmentBinding>(DiscoverMoviesFragmentBinding::inflate) {

    private lateinit var viewModel: DiscoverMoviesViewModel
    private lateinit var viewpagerMain: ViewPager
    private lateinit var tabs: TabLayout

    override fun setupViews() {
        viewpagerMain = binding.viewpagerMain
        tabs = binding.tabs
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpagerMain.adapter = MovieTypeAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewpagerMain)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiscoverMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
