package com.stameni.com.moviebrowser.screens.singleMovie

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.common.*
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.data.models.movie.MovieDetails
import com.stameni.com.moviebrowser.databinding.ActivitySingleMovieBinding
import com.stameni.com.moviebrowser.screens.news.NewsWebViewActivity
import com.stameni.com.moviebrowser.screens.singlePerson.SinglePersonActivity
import java.lang.Exception
import javax.inject.Inject

class SingleMovieActivity : BaseActivity<ActivitySingleMovieBinding>(ActivitySingleMovieBinding::inflate) {

    private lateinit var viewModel: SingleMovieViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var messageGenerator: MessageGenerator

    @Inject
    lateinit var intentGenerator: IntentGenerator

    private var imdbId = ""
    private var youtubeVideoKey = ""

    private var imagesAdapter: SingleMovieImagesAdapter? = null
    private var actorsAdapter: SingleMovieActorsAdapter? = null
    private var recommendationsAdapter: SingleMovieRecommendationsAdapter? = null

    private val imagesManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private val actorsManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
    private val movieRecommendations = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)

    private lateinit var toolbar: Toolbar
    private lateinit var watch_for_free_btn: Button
    private lateinit var imdb_rating: ImageView
    private lateinit var trailer_button: Button
    private lateinit var poster_image: ImageView
    private lateinit var rating: TextView
    private lateinit var movieImagesRv: RecyclerView
    private lateinit var movieActorsRv: RecyclerView
    private lateinit var movieRecommendationsRv: RecyclerView
    private lateinit var releaseDate: TextView
    private lateinit var movieDescription: TextView
    private lateinit var tmdbRating: TextView
    private lateinit var directorName: TextView
    private lateinit var genres: TextView
    private lateinit var runtime: TextView
    private lateinit var directorImage: ImageView
    private lateinit var directorsData: ConstraintLayout
    private lateinit var shareMovieButton: Button
    private lateinit var watchLaterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imagesAdapter = SingleMovieImagesAdapter(imageLoader)
        actorsAdapter = SingleMovieActorsAdapter(imageLoader)
        recommendationsAdapter = SingleMovieRecommendationsAdapter(imageLoader)

        initializeRecyclerViews()

        if (intent.extras != null) {
            viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(SingleMovieViewModel::class.java)

            val moviePosterUrl = intent.extras!!.getString(Constants.POSTER_URL, "")
            val movieId = intent.extras!!.getInt(Constants.MOVIE_ID, 0)
            val movieName = intent.extras!!.getString(Constants.MOVIE_NAME, "")

            Toast.makeText(this, "Scroll down to see more about $movieName", Toast.LENGTH_SHORT)
                .show()

            watch_for_free_btn.text = "CLICK HERE TO WATCH ${movieName.toUpperCase()} FOR FREE"

            watch_for_free_btn.setOnClickListener {
                generateGoogleNowQuery("Watch $movieName online for free")
            }

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

            imageLoader.loadImageNoFormat(moviePosterUrl, poster_image, Constants.LARGE_IMAGE_SIZE)

            viewModel.fetchSingleMovieDetails(movieId)

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

            viewModel.fetchError.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun setupViews() {
        toolbar = binding.toolbar
        watch_for_free_btn = binding.watchForFreeBtn
        imdb_rating = binding.imdbRating
        trailer_button = binding.trailerButton
        poster_image = binding.posterImage
        rating = binding.rating
        movieImagesRv = binding.movieImagesRv
        movieActorsRv = binding.movieActorsRv
        movieRecommendationsRv = binding.movieRecommendationsRv
        directorName = binding.directorName
        tmdbRating = binding.tmdbRating
        movieDescription = binding.movieDescription
        releaseDate = binding.releaseDate
        runtime = binding.runtime
        genres = binding.genres
        directorsData = binding.directorsData
        shareMovieButton = binding.shareMovieButton
        watchLaterButton = binding.watchLaterButton
        directorImage = binding.directorImage
    }

    private fun prepareMovieTrailer(youtubeVideoKey: String) {
        try {
            startActivity(intentGenerator.generateYoutubeTrailerIntent(youtubeVideoKey))
        } catch (ex: Exception) {
            Toast.makeText(
                this,
                "Trailer cannot be played. Do you have YouTube installed?",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun generateGoogleNowQuery(query: String) {
        startActivity(intentGenerator.generateGoogleNowIntent(query))
    }

    private fun initializeRecyclerViews() {
        movieImagesRv.adapter = imagesAdapter
        movieImagesRv.layoutManager = imagesManager

        movieActorsRv.layoutManager = actorsManager
        movieActorsRv.adapter = actorsAdapter
        movieActorsRv.addItemDecoration(DividerItemDecoration(this, RecyclerView.HORIZONTAL))

        movieRecommendationsRv.layoutManager = movieRecommendations
        movieRecommendationsRv.adapter = recommendationsAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun addMovieDetailsToScreen(details: MovieDetails) {
        if (details.directorImageUrl != null)
            imageLoader.loadImageFromTmdb(
                details.directorImageUrl,
                directorImage,
                null,
                Constants.LARGE_IMAGE_SIZE
            )
        if (!details.imdbId.isNullOrEmpty()) imdbId = details.imdbId
        directorName.text = details.directorName
        tmdbRating.text = "%.1f / 10".format(details.tmdbRating)
        movieDescription.text = details.movieDescription
        releaseDate.text = details.releaseDate
        runtime.text = "${details.runtime.toString()} min"
        genres.text = details.genres

        directorsData.setOnClickListener {
            if (details.directorId != 0) {
                goToSingleDirectorPage(details)
            }
        }

        shareMovieButton.setOnClickListener {
            createShareMovieMessage(details)
        }

        watchLaterButton.setOnClickListener {
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
        startActivity(messageGenerator.shareMovieMessageIntent(movieDetails))
    }

    private fun goToImdbPage(imdbId: String) {
        val url = "https://www.imdb.com/title/$imdbId"
        val intent = Intent(this, NewsWebViewActivity::class.java)
        intent.putExtra(Constants.SOURCE_LINK, url)
        startActivity(intent)
    }
}
