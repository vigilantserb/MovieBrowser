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
import com.stameni.com.moviebrowser.data.models.ListItem
import kotlinx.android.synthetic.main.top_list_movies_fragment.*
import javax.inject.Inject

class TopListMovies : BaseFragment() {

    private lateinit var viewModel: TopListMoviesViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

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
        val listItems = getLists()
        movie_recycler_view.adapter = TopListAdapter(listItems, imageLoader)
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
        //NEW
        listItems.add(
            ListItem(
                "Oscar - best picture winners",
                "/pzmrKXQgL7GEZvigD6W1bUEzXJN.jpg",
                "113560"
            )
        )
        listItems.add(
            ListItem(
                "24 Great Psychedelic Movies That Are Worth Your Time",
                "/tdgsBYmNCZAkxYQZuUVwVl56Yv1.jpg",
                "119015"
            )
        )
        listItems.add(
            ListItem(
                "The Golden Globes - Best Picture Winners",
                "/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg",
                "102340"
            )
        )
        listItems.add(
            ListItem(
                "Best Psychological Thrillers",
                "/s2bT29y0ngXxxu2IA8AOzzXTRhd.jpg",
                "34805"
            )
        )
        listItems.add(
            ListItem(
                "The best horror films of all time",
                "/dfNrZ82poQ8blHWJreIv6JZQ9JA.jpg",
                "8775"
            )
        )
        listItems.add(
            ListItem(
                "Most Mind-bending Movies",
                "/mMZRKb3NVo5ZeSPEIaNW9buLWQ0.jpg",
                "34806"
            )
        )
        listItems.add(
            ListItem(
                "30 Movies With A 100% Rating On Rotten Tomatoes",
                "/2OgxK3jTzi72EebK8V3bWiymaIW.jpg",
                "119018"
            )
        )
        listItems.add(
            ListItem(
                "The 10 Best Movies About Humanity's Relationship With Technology",
                "/pckdZ29bHj11hBsV3SbVVfmCB6C.jpg",
                "119017"
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
