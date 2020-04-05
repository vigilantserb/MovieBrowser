package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.certification

import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.ReleaseDatesResults

class FetchSingleMovieCertificationImpl : FetchSingleMovieCertification {

    override fun onCertificationFetched(response: List<ReleaseDatesResults>): String {
        var result = ""
        response.forEach {
            it.releaseDates?.let { releaseDates ->
                releaseDates.forEach {
                    if (it.releaseDate == "US")
                        result = it.certification
                }
            }
        }
        return result
    }
}