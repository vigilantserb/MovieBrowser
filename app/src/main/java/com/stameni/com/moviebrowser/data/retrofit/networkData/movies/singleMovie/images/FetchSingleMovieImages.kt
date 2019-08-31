package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.images

import com.stameni.com.moviebrowser.data.models.movie.MovieImage
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.Images

interface FetchSingleMovieImages {
    fun formatMovieImagesDataFromResponse(response: Images): ArrayList<MovieImage>
}