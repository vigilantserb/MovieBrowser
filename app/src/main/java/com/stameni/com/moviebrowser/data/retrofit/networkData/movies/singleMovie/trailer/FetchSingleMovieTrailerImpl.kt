package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.trailer

import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.VideoResults

class FetchSingleMovieTrailerImpl : FetchSingleMovieTrailer {

    override fun getTrailerLink(response: List<VideoResults>): String {
        var link = ""
        response.forEach {
            if (it.site == "YouTube" && it.type == "Trailer") link = it.key
        }

        return link
    }
}