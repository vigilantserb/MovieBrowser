package com.stameni.com.moviebrowser.screens.mylist.toWatch

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.databinding.ActivityToWatchBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ToWatchActivity : BaseActivity<ActivityToWatchBinding>(ActivityToWatchBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val compositeDisposable = CompositeDisposable()

    private lateinit var input_search: EditText
    private lateinit var movies_rv: RecyclerView

    override fun setupViews() {
        movies_rv = binding.moviesRv
        input_search = binding.inputSearch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToWatchViewModel::class.java)
        val layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        val adapter = ToWatchListAdapter(
            imageLoader,
            viewModel
        )

        compositeDisposable.add(input_search.textChangeEvents()
            .skipInitialValue()
            .map {
                it.text.toString().trim()
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.searchListWithTerm(it)
            })

        viewModel.fetchListMovies("toWatch")

        viewModel.fetchedMovies.observe(this, Observer {
            adapter.addAll(it)
        })

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        movies_rv.adapter = adapter
        movies_rv.layoutManager = layoutManager
    }
}
