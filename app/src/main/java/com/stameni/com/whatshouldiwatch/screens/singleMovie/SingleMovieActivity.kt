package com.stameni.com.whatshouldiwatch.screens.singleMovie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import com.stameni.com.whatshouldiwatch.data.models.MovieDetails
import com.stameni.com.whatshouldiwatch.screens.news.NewsWebViewActivity
import kotlinx.android.synthetic.main.activity_single_movie.*
import javax.inject.Inject

class SingleMovieActivity : BaseActivity() {

    private lateinit var viewModel: SingleMovieViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    var imdbId = ""
    var youtubeVideoKey = ""

    var imagesAdapter: SingleMovieImagesAdapter? = null
    var actorsAdapter:SingleMovieActorsAdapter? = null
    var recommendationsAdapter:SingleMovieRecommendationsAdapter? = null

    val imagesManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    val actorsManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
    val movieRecommendations = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imagesAdapter = SingleMovieImagesAdapter(ArrayList(), imageLoader)
        actorsAdapter = SingleMovieActorsAdapter(ArrayList(), imageLoader)
        recommendationsAdapter = SingleMovieRecommendationsAdapter(ArrayList(), imageLoader)

        initializeRecyclerViews()

        if (intent.extras != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleMovieViewModel::class.java)

            val moviePosterUrl = intent.extras!!.getString(Constants.POSTER_URL, "")
            val movieId = intent.extras!!.getInt(Constants.MOVIE_ID, 0)
            val movieName = intent.extras!!.getString(Constants.MOVIE_NAME, "")

            imdb_rating.setOnClickListener {
                if (imdbId.isNotEmpty()) {
                    createImdbLink(imdbId)
                }
            }

            trailer_button.setOnClickListener{
                if(youtubeVideoKey.isNotEmpty()){
                    val url = "https://www.youtube.com/watch?v=" + youtubeVideoKey
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setPackage("com.google.android.youtube")
                    startActivity(intent)
                }
            }

            supportActionBar!!.title = movieName

            imageLoader.loadPosterImageFitCenter(moviePosterUrl, poster_image, Constants.LARGE_IMAGE_SIZE)

            viewModel.fetchSingleMovieImages(movieId)
            viewModel.fetchSingleMovieActors(movieId)
            viewModel.fetchSingleMovieRecommendations(movieId)
            viewModel.fetchSingleMovieDetails(movieId)
            viewModel.fetchSingleMovieCertification(movieId)
            viewModel.fetchSingleMovieTrailer(movieId)

            viewModel.fetchedImages.observe(this, Observer {
                imagesAdapter!!.addAll(it)
            })

            viewModel.fetchedActors.observe(this, Observer {
                actorsAdapter!!.addAll(it)
            })

            viewModel.fetchedRecommendations.observe(this, Observer {
                recommendationsAdapter!!.addAll(it)
            })

            viewModel.fetchedCertification.observe(this, Observer {
                rating.text = it
            })

            viewModel.fetchedTrailerUrl.observe(this, Observer {
                youtubeVideoKey = it
            })

            viewModel.fetchedDetails.observe(this, Observer {
                addMovieDetailsToScreen(it)
            })
        }
    }

    private fun initializeRecyclerViews() {
        movie_images_rv.adapter = imagesAdapter
        movie_images_rv.layoutManager = imagesManager

        movie_actors_rv.layoutManager = actorsManager
        movie_actors_rv.adapter = actorsAdapter
        movie_actors_rv.addItemDecoration(DividerItemDecoration(this, RecyclerView.HORIZONTAL))

        movie_recommendations_rv.layoutManager = movieRecommendations
        movie_recommendations_rv.adapter = recommendationsAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun addMovieDetailsToScreen(details: MovieDetails) {
        if (details.directorImageUrl != null)
            imageLoader.loadImageFromTmdb(
                details.directorImageUrl,
                director_image,
                null,
                Constants.LARGE_IMAGE_SIZE
            )
        if (!details.imdbId.isNullOrEmpty()) imdbId = details.imdbId
        director_name.text = details.directorName
        tmdb_rating.text = "${details.tmdbRating.toString()} / 10"
        movie_description.text = details.movieDescription
        release_date.text = details.releaseDate
        runtime.text = "${details.runtime.toString()} min"
        genres.text = details.genres
    }

    private fun createImdbLink(imdbId: String){
        val url = "https://www.imdb.com/title/" + imdbId
        goToUrlAddress(url)
    }

    private fun goToUrlAddress(url: String) {
        val intent = Intent(this, NewsWebViewActivity::class.java)
        intent.putExtra(Constants.SOURCE_LINK, url)
        startActivity(intent)
    }
}
