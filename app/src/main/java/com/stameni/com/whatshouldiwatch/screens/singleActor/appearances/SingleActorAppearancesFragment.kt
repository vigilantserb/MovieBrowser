package com.stameni.com.whatshouldiwatch.screens.singleActor.appearances

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class SingleActorAppearancesFragment : Fragment() {

    companion object {
        fun newInstance() = SingleActorAppearancesFragment()
    }

    private lateinit var viewModel: SingleActorAppearancesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_actor_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SingleActorAppearancesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
