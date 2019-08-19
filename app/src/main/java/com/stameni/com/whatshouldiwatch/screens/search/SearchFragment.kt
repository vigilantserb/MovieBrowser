package com.stameni.com.whatshouldiwatch.screens.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    private var compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var movieSearchAdapter: SearchAdapter
    private lateinit var tvShowSearchAdapter: SearchAdapter
    private lateinit var peopleSearchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        input_search.requestFocus()
        val mgr = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.showSoftInput(input_search, InputMethodManager.SHOW_IMPLICIT)

        movieSearchAdapter = SearchAdapter(ArrayList(), imageLoader)
        tvShowSearchAdapter = SearchAdapter(ArrayList(), imageLoader)
        peopleSearchAdapter = SearchAdapter(ArrayList(), imageLoader)

        movie_recycler_view.adapter = movieSearchAdapter
        movie_recycler_view.layoutManager = LinearLayoutManager(view.context)
        movie_recycler_view.isNestedScrollingEnabled = false

        tv_show_recycler_view.adapter = tvShowSearchAdapter
        tv_show_recycler_view.layoutManager = LinearLayoutManager(view.context)
        tv_show_recycler_view.isNestedScrollingEnabled = false

        people_recycler_view.adapter = peopleSearchAdapter
        people_recycler_view.layoutManager = LinearLayoutManager(view.context)
        people_recycler_view.isNestedScrollingEnabled = false

        compositeDisposable.add(input_search.textChangeEvents()
            .skipInitialValue()
            .map {
                it.text.toString().trim()
            }
            .filter { it.length >= 3 }
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { searchQuery(it) })

        viewModel.fetchError.observe(this, Observer {
            progress_layout.visibility = View.GONE
            Log.e("Error", Log.getStackTraceString(it))
        })

        viewModel.fetchedData.observe(this, Observer { response ->
            progress_layout.visibility = View.GONE
            response?.forEach {
                when {
                    it.type == "Movie" -> {
                        movie_recycler_view.visibility = View.VISIBLE
                        movies_placeholder.visibility = View.VISIBLE
                        movieSearchAdapter.add(it)
                    }
                    it.type == "People" -> {
                        people_recycler_view.visibility = View.VISIBLE
                        people_placeholder.visibility = View.VISIBLE
                        peopleSearchAdapter.add(it)
                    }
                    else -> {
                        tv_show_recycler_view.visibility = View.VISIBLE
                        tv_show_placeholder.visibility = View.VISIBLE
                        tvShowSearchAdapter.add(it)
                    }
                }
            }
        })
    }

    private fun searchQuery(it: String) {
        movies_placeholder.visibility = View.GONE
        people_placeholder.visibility = View.GONE
        tv_show_placeholder.visibility = View.GONE
        progress_layout.visibility = View.VISIBLE
        movieSearchAdapter.removeAll()
        peopleSearchAdapter.removeAll()
        tvShowSearchAdapter.removeAll()
        viewModel.searchByTerm(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
