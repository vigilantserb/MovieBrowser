package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.images

import com.stameni.com.whatshouldiwatch.data.models.movie.MovieImage
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.Images

interface FetchSingleMovieImages {
    fun formatMovieImagesDataFromResponse(response: Images): ArrayList<MovieImage>
}