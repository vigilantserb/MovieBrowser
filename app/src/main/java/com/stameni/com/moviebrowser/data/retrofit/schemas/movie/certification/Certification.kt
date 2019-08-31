package com.stameni.com.moviebrowser.data.retrofit.schemas.movie.certification


import com.google.gson.annotations.SerializedName

data class Certification(
    @SerializedName("certification")
    val certification: String
)