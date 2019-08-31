package com.stameni.com.moviebrowser.screens.singlePerson.appearances

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.common.libraries.CustomSnackbar
import com.stameni.com.moviebrowser.screens.search.SearchAdapter
import kotlinx.android.synthetic.main.single_person_movies_fragment.*
import javax.inject.Inject

class SinglePersonAppearancesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: SinglePersonAppearancesViewModel
    private lateinit var adapter: SearchAdapter
    var currentPage = 1
    var lastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_person_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = SearchAdapter(ArrayList(), imageLoader)

        actor_movies_rv.adapter = adapter
        actor_movies_rv.layoutManager = layoutManager

        if (activity!!.intent.extras != null) {
            val personType = activity!!.intent.extras!!.getString(Constants.PERSON_TYPE, "")
            val personId = activity!!.intent.extras!!.getInt(Constants.PERSON_ID, 0)

            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SinglePersonAppearancesViewModel::class.java)

            fetchMoviesBasedOnPersonType(personType, personId, 1)

            initiateObservers()

            initiateRecyclerViewEndListener(personType, personId)
        }
    }

    private fun initiateRecyclerViewEndListener(
        personType: String,
        personId: Int
    ) {

        val snackbar = CustomSnackbar.make(view!!)
        snackbar.duration = 1000
        nested_scroll_view.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v!!.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    currentPage++
                    fetchMoviesBasedOnPersonType(personType, personId, currentPage)
                    if (!lastPage) if (!snackbar.isShown) snackbar.show()
                }
            }
        }
    }

    private fun initiateObservers() {
        viewModel.fetchedActorMovies.observe(this, Observer {
            if (it != null) {
                if (it.size < 20) lastPage = true
                adapter.addAll(it)
            }
        })

        viewModel.fetchedDirectorMovies.observe(this, Observer {
            if (it != null) {
                if (it.size < 20) lastPage = true
                adapter.addAll(it)
            }
        })
    }

    private fun fetchMoviesBasedOnPersonType(personType: String, personId: Int, page: Int) {
        when {
            personType.contains(Constants.ACTOR_TYPE) -> viewModel.getActorMovies(personId, page)
            personType.contains(Constants.DIRECTOR_TYPE) -> viewModel.getDirectorMovies(personId, page)
            else -> {
                viewModel.getActorMovies(personId, page)
                viewModel.getDirectorMovies(personId, page)
            }
        }
    }
}
