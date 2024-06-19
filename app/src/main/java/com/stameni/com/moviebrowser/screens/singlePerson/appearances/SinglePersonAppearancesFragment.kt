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
import com.stameni.com.moviebrowser.databinding.SinglePersonMoviesFragmentBinding
import com.stameni.com.moviebrowser.screens.search.SearchAdapter
import javax.inject.Inject

class SinglePersonAppearancesFragment :
    BaseFragment<SinglePersonMoviesFragmentBinding>(SinglePersonMoviesFragmentBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: SinglePersonAppearancesViewModel
    private lateinit var adapter: SearchAdapter

    private lateinit var actorMoviesRv: RecyclerView
    private lateinit var nestedScrollView: NestedScrollView

    private var currentPage = 1
    private var lastPage = false

    override fun setupViews() {
        actorMoviesRv = binding.actorMoviesRv
        nestedScrollView = binding.nestedScrollView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = SearchAdapter(imageLoader)

        actorMoviesRv.adapter = adapter
        actorMoviesRv.layoutManager = layoutManager

        if (requireActivity().intent.extras != null) {
            val personType = requireActivity().intent.extras!!.getString(Constants.PERSON_TYPE, "")
            val personId = requireActivity().intent.extras!!.getInt(Constants.PERSON_ID, 0)

            viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SinglePersonAppearancesViewModel::class.java)

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
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
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
            personType.contains(Constants.DIRECTOR_TYPE) -> viewModel.getDirectorMovies(
                personId,
                page
            )

            else -> {
                viewModel.getActorMovies(personId, page)
                viewModel.getDirectorMovies(personId, page)
            }
        }
    }
}
