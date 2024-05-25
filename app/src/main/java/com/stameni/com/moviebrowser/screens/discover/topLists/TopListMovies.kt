package com.stameni.com.moviebrowser.screens.discover.topLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.data.local.FetchListOfTopMoviesUseCase
import kotlinx.android.synthetic.main.top_list_movies_fragment.*
import javax.inject.Inject

class TopListMovies : BaseFragment() {

    @Inject
    lateinit var imageLoader: ImageLoader
    
    @Inject
    lateinit var fetchListOfTopMovies: FetchListOfTopMoviesUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_list_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager = LinearLayoutManager(context)
        movie_recycler_view.adapter = TopListAdapter(fetchListOfTopMovies.getData(), imageLoader)
    }
}
