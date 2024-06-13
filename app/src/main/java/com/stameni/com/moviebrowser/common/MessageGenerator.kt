package com.stameni.com.moviebrowser.common

import android.content.Intent
import com.stameni.com.moviebrowser.data.models.movie.MovieDetails

class MessageGenerator(private val intentGenerator: IntentGenerator) {

    fun shareMovieMessageIntent(movieDetails: MovieDetails): Intent {
        movieDetails.apply {
            val movieDate = releaseDate?.let {
                releaseDate.removeRange(4, releaseDate.length)
            }

            val txtMsg =
                "Hey! Check out $movieTitle | $movieDate " +
                        "directed by $directorName. Rated $tmdbRating on TMDB!"
            return intentGenerator.sendTextMessageIntent(txtMsg)
        }
    }
}