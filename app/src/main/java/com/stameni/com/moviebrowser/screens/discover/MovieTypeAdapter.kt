package com.stameni.com.moviebrowser.screens.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.stameni.com.moviebrowser.screens.discover.genre.GenreMovies
import com.stameni.com.moviebrowser.screens.discover.nowPlaying.NowPlayingMovies
import com.stameni.com.moviebrowser.screens.discover.topLists.TopListMovies
import com.stameni.com.moviebrowser.screens.discover.upcoming.UpcomingMovies

class MovieTypeAdapter(childFragmentManager: FragmentManager) : FragmentPagerAdapter(childFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> TopListMovies()
            1 -> GenreMovies()
            2 -> NowPlayingMovies()
            else -> UpcomingMovies()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Top lists"
            1 -> "Genres"
            2 -> "Now playing"
            else -> "Upcoming"
        }
    }
}
