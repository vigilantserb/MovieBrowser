package com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pollux.widget.DualProgressView
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.common.libraries.CustomSnackbar
import com.stameni.com.moviebrowser.databinding.ActivityMovieGridBinding
import javax.inject.Inject

class MovieGridActivity : BaseActivity<ActivityMovieGridBinding>(ActivityMovieGridBinding::inflate) {

    private val FIRST_PAGE = 1
    private var currentPage = 1

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: MovieGridViewModel

    private var totalPages = 0

    private lateinit var toolbar: Toolbar
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var rootView: ConstraintLayout
    private lateinit var gifProgressBar: DualProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gridLayoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        val adapter = MovieGridAdapter(imageLoader)

        movieRecyclerView.layoutManager = gridLayoutManager
        movieRecyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieGridViewModel::class.java)


        if(intent.extras != null){
            val genreId = intent.extras!!.getInt("genreId", 0)
            val genreName = intent.extras!!.getString("genreName", "")

            supportActionBar!!.title = genreName

            val snackbar = CustomSnackbar.make(rootView)
            snackbar.duration = 1000

            viewModel.getListMovies(genreId, FIRST_PAGE)

            movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1) {
                        if(currentPage <= totalPages){
                            currentPage++
                            viewModel.getListMovies(genreId, currentPage)
                            if (!snackbar.isShown) {
                                snackbar.show()
                            }
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })

            viewModel.totalPages.observe(this, Observer {
                if(it != null){
                    totalPages = it
                }
            })

            viewModel.fetchedMovies.observe(this, Observer {
                if (it != null) {
                    if(gifProgressBar.visibility == View.VISIBLE) gifProgressBar.visibility = View.GONE
                    adapter.addAll(it)
                }
            })

            viewModel.fetchError.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun setupViews() {
        toolbar = binding.toolbar
        movieRecyclerView = binding.movieRecyclerView
        rootView = binding.rootView
        gifProgressBar = binding.gifProgressBar
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
