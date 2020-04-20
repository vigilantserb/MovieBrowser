package com.stameni.com.moviebrowser.data.retrofit.networkData.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.moviebrowser.data.models.Genre
import com.stameni.com.moviebrowser.data.retrofit.MovieApi
import com.stameni.com.moviebrowser.data.retrofit.schemas.genre.GenreListSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FetchGenreListUseCaseImpl constructor(
    private val movieApi: MovieApi
) : FetchGenreListUseCase {

    override val fetchError = MutableLiveData<String>()

    private val _genreListLiveData: MutableLiveData<List<Genre>> = MutableLiveData()

    override val genreListLiveData: LiveData<List<Genre>>
        get() = _genreListLiveData


    /**
     * Fetches genre list, and each genre receives an image locally to be displayed.
     * */
    override fun getGenreList(): Disposable {
        return movieApi
            .getDbGenres()
            .subscribeOn(Schedulers.io())
            .map {
                formatGenreList(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onGenreListFetch(it) }, { onGenreListFetchFail(it as Exception) }
            )
    }

    private fun formatGenreList(response: Response<GenreListSchema>): ArrayList<Genre> {
        val genreList = ArrayList<Genre>()
        val imageList = getGenreImages()

        response.body()?.let {
            it.genres.forEachIndexed { index, it ->
                genreList.add(Genre(it.name, it.id, imageList[index]))
            }
        }
        return genreList
    }

    private fun onGenreListFetch(result: ArrayList<Genre>) {
        _genreListLiveData.value = result
    }

    private fun getGenreImages(): Array<String> = arrayOf(
        "/w2PMyoyLU22YvrGK3smVM9fW1jj.jpg",
        "/v4yVTbbl8dE1UP2dWu5CLyaXOku.jpg",
        "/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg",
        "/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg",
        "/vVpEOvdxVBP2aV166j5Xlvb5Cdc.jpg",
        "/6hfmHRaZNKzazyDDLli5CbT6L8H.jpg",
        "/oAr5bgf49vxga9etWoQpAeRMvhp.jpg",
        "/hziiv14OpD73u9gAak4XDDfBKa2.jpg",
        "/5kYj5EOQMFBFCdnk4X8KaFUfDVR.jpg",
        "/hjQp148VjWF4KjzhsD90OCMr11h.jpg",
        "/AiG8iWpFtFSXAdhStWu6qBaqmv9.jpg",
        "/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg",
        "/8ZNGBfGoN3uI5Akj5vEUDMxvmGO.jpg",
        "/xqQztbT6KlPLQLlRtNHoXivEMZA.jpg",
        "/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg",
        "/zTyrr7jlOl9SC7vfA4uLq8n8j2N.jpg",
        "/mFb0ygcue4ITixDkdr7wm1Tdarx.jpg",
        "/aPEhtVLrZRnJufKHwbHgqwirv7J.jpg",
        "/qUcmEqnzIwlwZxSyTf3WliSfAjJ.jpg"
    )

    private fun onGenreListFetchFail(exception: Exception) {
        fetchError.value =
            "An error occurred while fetching genre list - ${exception.localizedMessage}"
    }
}