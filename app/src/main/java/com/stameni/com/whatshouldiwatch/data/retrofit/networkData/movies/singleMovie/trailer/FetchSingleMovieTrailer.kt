package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.trailer

import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.VideoResults

interface FetchSingleMovieTrailer {
    fun getTrailerLink(response: List<VideoResults>): String
}