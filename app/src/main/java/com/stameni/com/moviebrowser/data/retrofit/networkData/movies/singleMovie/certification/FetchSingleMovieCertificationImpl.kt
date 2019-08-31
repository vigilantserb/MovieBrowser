package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.certification

import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.ReleaseDatesResults

class FetchSingleMovieCertificationImpl : FetchSingleMovieCertification {

    override fun onCertificationFetched(response: List<ReleaseDatesResults>): String {
        var result = ""
        response.forEach {
            if (it.releaseDates != null) {
                it.releaseDates.forEach {
                    if (it.releaseDate == "US")
                        result = it.certification
                }
            }
        }
        return result
    }
}