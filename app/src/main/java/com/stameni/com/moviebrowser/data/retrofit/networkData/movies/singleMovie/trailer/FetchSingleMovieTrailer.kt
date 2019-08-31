package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.trailer

import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.VideoResults

interface FetchSingleMovieTrailer {
    fun getTrailerLink(response: List<VideoResults>): String
}