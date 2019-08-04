package com.stameni.com.whatshouldiwatch.screens.singleActor

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.whatshouldiwatch.R
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

            val name = intent.extras!!.getString("actorName", "")
            val id = intent.extras!!.getInt("actorId", 0)
            val url = intent.extras!!.getString("actorUrl", "")

            supportActionBar!!.title = name

            imageLoader.loadImageBlurCenterCrop(url, person_profile_blurred, "w500")
            imageLoader.loadImageNoFormat(url, person_profile, "w500")

            viewModel.fetchActorDetails(id)

            viewModel.actorDetails.observe(this, Observer {
                person_age.text = "${it.actorAge} years old"
                person_name.text = it.actorName
                person_pob.text = it.actorBirthplace
                person_movies.text = it.actorMovies
            })
        }
    }
}
