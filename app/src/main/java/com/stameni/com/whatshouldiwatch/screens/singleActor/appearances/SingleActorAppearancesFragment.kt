package com.stameni.com.whatshouldiwatch.screens.singleActor.appearances

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.screens.search.SearchAdapter
import kotlinx.android.synthetic.main.single_actor_movies_fragment.*
import javax.inject.Inject

class SingleActorAppearancesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: SingleActorAppearancesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_actor_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        var layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        var adapter = SearchAdapter(ArrayList(), imageLoader)

        actor_movies_rv.adapter = adapter
        actor_movies_rv.layoutManager = layoutManager

        if (activity!!.intent.extras != null) {
            val id = activity!!.intent.extras!!.getInt("actorId", 0)

            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleActorAppearancesViewModel::class.java)

            viewModel.getActorMovies(id)
            viewModel.fetchedMovies.observe(this, Observer {
                if (it != null) {
                    adapter.addAll(it)
                }
            })
        }
    }
}
