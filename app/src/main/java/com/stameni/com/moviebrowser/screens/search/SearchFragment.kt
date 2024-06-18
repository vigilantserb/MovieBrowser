package com.stameni.com.moviebrowser.screens.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.SearchFragmentBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {

    private var compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var movieSearchAdapter: SearchAdapter
    private lateinit var tvShowSearchAdapter: SearchAdapter
    private lateinit var peopleSearchAdapter: SearchAdapter

    private lateinit var inputSearch: EditText
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var tvShowRecyclerView: RecyclerView
    private lateinit var peopleRecyclerView: RecyclerView
    private lateinit var progressLayout: LinearLayout
    private lateinit var moviesPlaceholder: TextView
    private lateinit var peoplePlaceholder: TextView
    private lateinit var tvShowPlaceholder: TextView

    override fun setupViews() {
        inputSearch = binding.inputSearch
        movieRecyclerView = binding.movieRecyclerView
        tvShowRecyclerView = binding.tvShowRecyclerView
        peopleRecyclerView = binding.peopleRecyclerView
        progressLayout = binding.progressLayout
        moviesPlaceholder = binding.moviesPlaceholder
        peoplePlaceholder = binding.peoplePlaceholder
        tvShowPlaceholder = binding.tvShowPlaceholder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        inputSearch.requestFocus()
        val mgr = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.showSoftInput(inputSearch, InputMethodManager.SHOW_IMPLICIT)

        movieSearchAdapter = SearchAdapter(ArrayList(), imageLoader)
        tvShowSearchAdapter = SearchAdapter(ArrayList(), imageLoader)
        peopleSearchAdapter = SearchAdapter(ArrayList(), imageLoader)

        movieRecyclerView.adapter = movieSearchAdapter
        movieRecyclerView.layoutManager = LinearLayoutManager(view.context)
        movieRecyclerView.isNestedScrollingEnabled = false

        tvShowRecyclerView.adapter = tvShowSearchAdapter
        tvShowRecyclerView.layoutManager = LinearLayoutManager(view.context)
        tvShowRecyclerView.isNestedScrollingEnabled = false

        peopleRecyclerView.adapter = peopleSearchAdapter
        peopleRecyclerView.layoutManager = LinearLayoutManager(view.context)
        peopleRecyclerView.isNestedScrollingEnabled = false

        compositeDisposable.add(inputSearch.textChangeEvents()
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
            progressLayout.visibility = View.GONE
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.fetchedData.observe(this, Observer { response ->
            progressLayout.visibility = View.GONE
            response?.forEach {
                when {
                    it.type == "Movie" -> {
                        movieRecyclerView.visibility = View.VISIBLE
                        moviesPlaceholder.visibility = View.VISIBLE
                        movieSearchAdapter.add(it)
                    }
                    it.type == "People" -> {
                        peopleRecyclerView.visibility = View.VISIBLE
                        peoplePlaceholder.visibility = View.VISIBLE
                        peopleSearchAdapter.add(it)
                    }
                    else -> {
                        tvShowRecyclerView.visibility = View.VISIBLE
                        tvShowPlaceholder.visibility = View.VISIBLE
                        tvShowSearchAdapter.add(it)
                    }
                }
            }
        })
    }

    private fun searchQuery(it: String) {
        moviesPlaceholder.visibility = View.GONE
        peoplePlaceholder.visibility = View.GONE
        tvShowPlaceholder.visibility = View.GONE
        progressLayout.visibility = View.VISIBLE
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
