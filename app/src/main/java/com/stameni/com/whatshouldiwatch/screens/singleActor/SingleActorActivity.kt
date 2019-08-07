package com.stameni.com.whatshouldiwatch.screens.singleActor

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_single_actor.*
import javax.inject.Inject

class SingleActorActivity : BaseActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SingleActorActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_single_actor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpager_main.adapter = SingleActorViewPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager_main)

        if(intent.extras != null){
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleActorActivityViewModel::class.java)

            val name = intent.extras!!.getString(Constants.ACTOR_NAME, "")
            val id = intent.extras!!.getInt(Constants.ACTOR_ID, 0)
            val url = intent.extras!!.getString(Constants.ACTOR_IMAGE_URL, "")

            supportActionBar!!.title = name

            imageLoader.loadImageBlurCenterCrop(url, person_profile_blurred, Constants.LARGE_IMAGE_SIZE)
            imageLoader.loadImageNoFormat(url, person_profile, Constants.LARGE_IMAGE_SIZE)

            viewModel.fetchPersonDetails(id)

            viewModel.personDetails.observe(this, Observer {
                person_age.text = "${it.personAge} years old"
                person_name.text = it.personName
                person_pob.text = it.personBirthplace
                person_movies.text = it.personMovieCount
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
