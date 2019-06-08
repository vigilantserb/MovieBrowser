package com.stameni.com.whatshouldiwatch.screens.discover.genre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.BaseFragment
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        controllerComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GenreMoviesViewModel::class.java)

        viewModel.getGenreList()

        viewModel.fetchedGenres.observe(this, Observer {
            println(it[0].genreName)
        })

        viewModel.fetchError.observe(this, Observer {
            Log.e("test", Log.getStackTraceString(it))
        })

    }

}
