package com.stameni.com.whatshouldiwatch.screens.discover.genre

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.BaseFragment
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.data.MovieApi
import com.stameni.com.whatshouldiwatch.data.networkdata.FetchGenreListUseCaseImpl
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
            if(it != null){
                println(it[0].genreName)
            }else{
                println("jbg null")
            }
        })

        // TODO: Use the ViewModel
    }

}
