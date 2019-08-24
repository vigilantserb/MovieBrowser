package com.stameni.com.whatshouldiwatch.screens.singleMovie

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.*
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import com.stameni.com.whatshouldiwatch.data.models.movie.MovieDetails
import com.stameni.com.whatshouldiwatch.screens.news.NewsWebViewActivity
import com.stameni.com.whatshouldiwatch.screens.singlePerson.SinglePersonActivity
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
    var actorsAdapter: SingleMovieActorsAdapter? = null
    var recommendationsAdapter: SingleMovieRecommendationsAdapter? = null

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
                    goToImdbPage(imdbId)
                }
            }

            trailer_button.setOnClickListener {
                if (youtubeVideoKey.isNotEmpty()) {
                    prepareMovieTrailer(youtubeVideoKey)
                }
            }

            supportActionBar!!.title = movieName

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = (displayMetrics.heightPixels / displayMetrics.density).toInt()
            val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()

            // Calculate ActionBar height
            val tv = TypedValue()
            var actionBarHeight = 0
            if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            }

            val param = ConstraintLayout.LayoutParams(
                displayMetrics.widthPixels,
                displayMetrics.heightPixels - actionBarHeight - getStatusBarHeight() + 28
            )
            poster_image.layoutParams = param

            imageLoader.loadPosterImageCenterCrop(moviePosterUrl, poster_image, Constants.LARGE_IMAGE_SIZE)

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

            viewModel.saveMovieMessage.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun prepareMovieTrailer(youtubeVideoKey: String) {
        startActivity(LinkGenerator.generateYoutubeTrailerIntent(youtubeVideoKey))
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

        directors_data.setOnClickListener {
            if (details.directorId != 0) {
                goToSingleDirectorPage(details)
            }
        }

        share_movie_button.setOnClickListener {
            createShareMovieMessage(details)
        }

        watch_later_button.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.saveMovieToDatabase(details, "watched")
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        viewModel.saveMovieToDatabase(details, "toWatch")
                    }
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Which list would you like to add ${details.movieTitle} to?")
                .setPositiveButton("Watched", dialogClickListener)
                .setNegativeButton("Watch later", dialogClickListener).show()
        }
    }

    private fun goToSingleDirectorPage(movieDetails: MovieDetails) {
        val intent = Intent(this, SinglePersonActivity::class.java)
        intent.putExtra(Constants.PERSON_TYPE, Constants.DIRECTOR_TYPE)
        intent.putExtra(Constants.PERSON_NAME, movieDetails.directorName)
        intent.putExtra(Constants.PERSON_ID, movieDetails.directorId)
        intent.putExtra(Constants.PERSON_IMAGE_URL, movieDetails.directorImageUrl)
        startActivity(intent)
    }

    private fun createShareMovieMessage(movieDetails: MovieDetails) {
        startActivity(MessageGenerator.shareMovieMessageIntent(movieDetails))
    }

    private fun goToImdbPage(imdbId: String) {
        val url = "https://www.imdb.com/title/$imdbId"
        val intent = Intent(this, NewsWebViewActivity::class.java)
        intent.putExtra(Constants.SOURCE_LINK, url)
        startActivity(intent)
    }
}
