package com.stameni.com.whatshouldiwatch.screens.singleActor.biography

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class SingleActorBiographyFragment : Fragment() {

    private lateinit var viewModel: SingleActorBiographyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_actor_biography_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SingleActorBiographyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
