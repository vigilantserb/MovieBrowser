package com.stameni.com.whatshouldiwatch.screens.singleMovie

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_single_movie.*
import javax.inject.Inject

class SingleMovieActivity : BaseActivity() {

    private lateinit var viewModel: SingleMovieViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imagesAdapter = SingleMovieImagesAdapter(ArrayList(), imageLoader)
        val actorsAdapter = SingleMovieActorsAdapter(ArrayList(), imageLoader)
        val recommendationsAdapter = SingleMovieRecommendationsAdapter(ArrayList(), imageLoader)

        val imagesManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val actorsManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        val movieRecommendations = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)

        movie_images_rv.adapter = imagesAdapter
        movie_images_rv.layoutManager = imagesManager

        movie_actors_rv.layoutManager = actorsManager
        movie_actors_rv.adapter = actorsAdapter
        movie_actors_rv.addItemDecoration(DividerItemDecoration(this, RecyclerView.HORIZONTAL))

        movie_recommendations_rv.layoutManager = movieRecommendations
        movie_recommendations_rv.adapter = recommendationsAdapter

        if (intent.extras != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleMovieViewModel::class.java)

            val url = intent.extras!!.getString("posterUrl", "")
            val id = intent.extras!!.getInt("movieId", 0)
            val name = intent.extras!!.getString("movieName", "")

            supportActionBar!!.title = name

            //TODO Use ImageLoader class here as well
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/$url")
                .error(R.drawable.ic_placeholder)
                .fitCenter()
                .into(poster_image)

            viewModel.fetchSingleMovieImages(id)
            viewModel.fetchSingleMovieActors(id)
            viewModel.fetchSingleMovieRecommendations(id)
            viewModel.fetchSingleMovieDetails(id)

            viewModel.fetchedImages.observe(this, Observer {
                if (it != null) {
                    imagesAdapter.addAll(it)
                }
            })

            viewModel.fetchedActors.observe(this, Observer {
                if (it != null) {
                    actorsAdapter.addAll(it)
                }
            })

            viewModel.fetchedRecommendations.observe(this, Observer {
                if (it != null) {
                    recommendationsAdapter.addAll(it)
                }
            })

            viewModel.fetchedDetails.observe(this, Observer {
                if (it != null) {
                    if (it.directorImageUrl != null)
                        imageLoader.loadImageFromTmdb(
                            it.directorImageUrl,
                            director_image,
                            null,
                            "w500"
                        )
                    director_name.text = it.directorName
                    imdb_rating.text = "${it.imdbRating.toString()} / 10"
                    tmdb_rating.text = "${it.tmdbRating.toString()} / 10"
                    movie_description.text = it.movieDescription
                    release_date.text = it.releaseDate
                    runtime.text = "${it.runtime.toString()} min"
                    rating.text = "Not found" //TODO placeholder
                    genres.text = it.genres
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
