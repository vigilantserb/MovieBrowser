package com.stameni.com.moviebrowser.common


class Constants {

    companion object {
        //GLOBAL
        const val SUCCESS = 1
        const val UNSUCCESSFUL = -1

        const val MOVIE_TYPE = "Movie"
        const val TV_SHOW_TYPE = "TV Show"
        const val PEOPLE_TYPE = "People"

        //Movie data
        const val POSTER_URL = "posterUrl"
        const val MOVIE_ID = "movieId"
        const val MOVIE_NAME = "movieTitle"

        //Person data
        const val PERSON_TYPE = "personType"
        const val PERSON_NAME = "name"
        const val PERSON_ID = "personId"
        const val PERSON_IMAGE_URL = "personImageUrl"

        //Actor
        const val ACTOR_TYPE = "actorType"

        //Director
        const val DIRECTOR_TYPE = "directorType"

        //Image sizes
        const val LARGE_IMAGE_SIZE = "w500"
        const val SMALL_IMAGE_SIZE = "w92"

        //Links
        const val SOURCE_LINK = "source"
    }
}