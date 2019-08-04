package com.stameni.com.whatshouldiwatch.screens.singleActor.biography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import kotlinx.android.synthetic.main.single_actor_biography_fragment.*
import javax.inject.Inject

class SingleActorBiographyFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SingleActorBiographyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_actor_biography_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        if(activity != null){
            if(activity!!.intent.extras != null){
                val id = activity!!.intent.extras!!.getInt("actorId", 0)

                viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleActorBiographyViewModel::class.java)

                viewModel.fetchActorDetails(id)

                viewModel.actorDetails.observe(this, Observer {
                    actor_biography.text = it.actorBiography
                })
            }
        }
    }
}
