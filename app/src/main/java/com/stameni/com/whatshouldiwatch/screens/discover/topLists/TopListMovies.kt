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
        var listItems = getLists()
        recycler_view.adapter = MovieListAdapter(listItems)
    }

    private fun getLists(): List<MovieListItem> {
        var listItems = ArrayList<MovieListItem>()

        listItems.add(MovieListItem("The DC Comics Universe", "/f6ljQGv7WnJuwBPty017oPWfqjx.jpg", "3"))
        listItems.add(MovieListItem("Best picture Winners - The Academy awards", "/rgyhSn3mINvkuy9iswZK0VLqQO3.jpg", "28"))
        listItems.add(MovieListItem("Greatest Twist Ending", "/ld7huGVoyXAY6EsdAUeLh9QxAl6.jpg", "1131"))
        listItems.add(MovieListItem("IMDb Top 250", "/8LZ0r7r2SdJIApRvGo2k6CXHq8x.jpg", "1309"))
        listItems.add(MovieListItem("Best Picture Winners - The Golden Globes", "/tNpJuz8NEG0DsGG8SN0dL2kbCzs.jpg", "2469"))
        listItems.add(MovieListItem("Disney Classic Collection", "/jxWA2lDLidicmmDymnxlz53zAb9.jpg", "338"))
        //listItems.add(MovieListItem("Most Mind-bending Movies", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("All Pixar Feature Movies (1995-2017)", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("30 Movies With A 100% Rating On Rotten Tomatoes", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Comic Book Movies Coming Out In 2017", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("The 10 Best Movies About Humanity's Relationship With Technology", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("24 Great Psychedelic Movies That Are Worth Your Time", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("The 10 Best Comedies of 2016", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("10 Great Movies With The Most Enjoyable Conversations", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top 10 Best Gangster Movies Of All Time", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top 10 Best Netflix Originals Movies", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top 15 Animated movies On Netflix", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top Love Movies Of All Time", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Greatest Films About Real Life Heroes", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top 15 Monster Movies Of All Time", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("2019 Oscar Nominations", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("The Avengers: The Complete List", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))
//        listItems.add(MovieListItem("Top 13 Zombie Movies Of All Time", "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg", "3"))

        return listItems
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopListMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
