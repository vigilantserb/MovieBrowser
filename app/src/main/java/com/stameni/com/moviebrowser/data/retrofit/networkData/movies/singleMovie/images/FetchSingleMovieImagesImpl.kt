package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.images

import com.stameni.com.moviebrowser.data.models.movie.MovieImage
import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.Images

class FetchSingleMovieImagesImpl : FetchSingleMovieImages {

    override fun formatMovieImagesDataFromResponse(response: Images): ArrayList<MovieImage> {
        var formattedData = ArrayList<MovieImage>()

        response.backdrops?.let { images ->
            images.forEach {
                formattedData.add(
                    MovieImage(
                        it.filePath,
                        it.voteAverage
                    )
                )
            }
        }

        formattedData = ArrayList(formattedData.sortedWith(compareByDescending { it.imageRating }))

        return formattedData
    }
}