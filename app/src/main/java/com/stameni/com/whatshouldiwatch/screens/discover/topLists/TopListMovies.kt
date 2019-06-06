package com.stameni.com.whatshouldiwatch.screens.discover.topLists

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.stameni.com.whatshouldiwatch.R
import kotlinx.android.synthetic.main.top_list_movies_fragment.*

class TopListMovies : Fragment() {

    companion object {
        fun newInstance() = TopListMovies()
    }
    //list ids - 3, 28, 1131, 1309,
    private lateinit var viewModel: TopListMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_list_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        var listItems = ArrayList<MovieListItem>()
        listItems.add(MovieListItem("Title 1", "Link 1"))
        listItems.add(MovieListItem("Title 1", "Link 1"))
        listItems.add(MovieListItem("Title 1", "Link 1"))
        listItems.add(MovieListItem("Title 1", "Link 1"))
        listItems.add(MovieListItem("Title 1", "Link 1"))
        recycler_view.adapter = MovieListAdapter(listItems)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopListMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
