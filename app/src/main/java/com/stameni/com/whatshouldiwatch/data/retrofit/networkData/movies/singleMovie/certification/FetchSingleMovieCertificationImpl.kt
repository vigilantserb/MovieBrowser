package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.certification

import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.ReleaseDatesResults

class FetchSingleMovieCertificationImpl : FetchSingleMovieCertification {

    override fun onActorsFetched(response: List<ReleaseDatesResults>): String {
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