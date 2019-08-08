package com.stameni.com.whatshouldiwatch.data.schemas.person.singlePerson


import com.google.gson.annotations.SerializedName

data class CombinedCredits(
    @SerializedName("cast")
    val cast: List<Cast>?,
    @SerializedName("crew")
    val crew: List<Crew>?
)