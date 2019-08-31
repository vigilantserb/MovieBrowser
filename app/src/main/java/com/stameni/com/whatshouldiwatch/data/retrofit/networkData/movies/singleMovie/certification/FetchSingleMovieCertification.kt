package com.stameni.com.whatshouldiwatch.data.retrofit.networkData.movies.singleMovie.certification

import com.stameni.com.whatshouldiwatch.data.retrofit.schemas.movie.details.ReleaseDatesResults

interface FetchSingleMovieCertification {
    fun onCertificationFetched(response: List<ReleaseDatesResults>): String
}