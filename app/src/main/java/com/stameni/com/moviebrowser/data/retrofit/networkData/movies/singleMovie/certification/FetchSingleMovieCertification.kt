package com.stameni.com.moviebrowser.data.retrofit.networkData.movies.singleMovie.certification

import com.stameni.com.moviebrowser.data.retrofit.schemas.movie.details.ReleaseDatesResults

interface FetchSingleMovieCertification {
    fun onCertificationFetched(response: List<ReleaseDatesResults>): String
}