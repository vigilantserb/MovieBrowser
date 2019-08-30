package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.images

import com.stameni.com.whatshouldiwatch.data.models.movie.MovieImage
import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.Images

class FetchSingleMovieImagesImpl : FetchSingleMovieImages {

    override fun formatMovieImagesDataFromResponse(response: Images): ArrayList<MovieImage> {
        var formattedData = ArrayList<MovieImage>()

        if (response.backdrops != null) {
            response.backdrops.forEach {
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