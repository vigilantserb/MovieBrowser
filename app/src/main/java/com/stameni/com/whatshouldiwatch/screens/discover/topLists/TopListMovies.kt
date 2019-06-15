package com.stameni.com.whatshouldiwatch.screens.discover.topLists

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.data.models.ListItem
import kotlinx.android.synthetic.main.top_list_movies_fragment.*

class TopListMovies : Fragment() {

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
        var listItems = getLists()
        recycler_view.adapter = TopListAdapter(listItems)
    }

    private fun getLists(): List<ListItem> {
        var listItems = ArrayList<ListItem>()

        listItems.add(
            ListItem(
                "The DC Comics Universe",
                "/f6ljQGv7WnJuwBPty017oPWfqjx.jpg",
                "3"
            )
        )
        listItems.add(
            ListItem(
                "Best picture Winners - The Academy awards",
                "/rgyhSn3mINvkuy9iswZK0VLqQO3.jpg",
                "28"
            )
        )
        listItems.add(
            ListItem(
                "Greatest Twist Ending",
                "/ld7huGVoyXAY6EsdAUeLh9QxAl6.jpg",
                "1131"
            )
        )
        listItems.add(
            ListItem(
                "IMDb Top 250",
                "/8LZ0r7r2SdJIApRvGo2k6CXHq8x.jpg",
                "1309"
            )
        )
        listItems.add(
            ListItem(
                "Best Picture Winners - The Golden Globes",
                "/tNpJuz8NEG0DsGG8SN0dL2kbCzs.jpg",
                "2469"
            )
        )
        listItems.add(
            ListItem(
                "Disney Classic Collection",
                "/jxWA2lDLidicmmDymnxlz53zAb9.jpg",
                "338"
            )
        )

        return listItems
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopListMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
