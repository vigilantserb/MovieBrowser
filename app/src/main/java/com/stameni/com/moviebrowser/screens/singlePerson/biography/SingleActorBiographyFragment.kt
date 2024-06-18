package com.stameni.com.moviebrowser.screens.singlePerson.biography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.databinding.SinglePersonBiographyFragmentBinding
import javax.inject.Inject

class SingleActorBiographyFragment :
    BaseFragment<SinglePersonBiographyFragmentBinding>(SinglePersonBiographyFragmentBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SingleActorBiographyViewModel

    private lateinit var personBiography: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_person_biography_fragment, container, false)
    }

    override fun setupViews() {
        personBiography = binding.personBiography
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        if (activity != null) {
            if (requireActivity().intent.extras != null) {
                val id = requireActivity().intent.extras!!.getInt(Constants.PERSON_ID, 0)

                viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(SingleActorBiographyViewModel::class.java)

                viewModel.fetchPersonBiography(id, "")

                viewModel.fetchedBiography.observe(this, Observer {
                    personBiography.text = it.biography
                })
            }
        }
    }
}
