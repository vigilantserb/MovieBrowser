package com.stameni.com.moviebrowser.common

import android.content.Intent
import com.stameni.com.moviebrowser.data.models.movie.MovieDetails

class MessageGenerator {
    companion object{
        fun shareMovieMessageIntent(movieDetails: MovieDetails): Intent {
            var movieDate = ""
            if (movieDetails.releaseDate != null) {
                movieDate = movieDetails.releaseDate.removeRange(4, movieDetails.releaseDate.length)
            }
            val string =
                "Hey! Check out ${movieDetails.movieTitle} | $movieDate directed by ${movieDetails.directorName}. Rated ${movieDetails.tmdbRating} on TMDB!"
            return Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, string)
                type = "text/plain"
            }
        }
    }
}