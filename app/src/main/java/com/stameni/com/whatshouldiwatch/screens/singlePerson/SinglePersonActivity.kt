package com.stameni.com.whatshouldiwatch.screens.singlePerson

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import kotlinx.android.synthetic.main.activity_single_person.*
import javax.inject.Inject

class SinglePersonActivity : BaseActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SinglePersonActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_single_person)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpager_main.adapter = SinglePersonViewPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager_main)

        if (intent.extras != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SinglePersonActivityViewModel::class.java)

            val type = intent.extras!!.getString(Constants.PERSON_TYPE, "")
            val name = intent.extras!!.getString(Constants.PERSON_NAME, "")
            val id = intent.extras!!.getInt(Constants.PERSON_ID, -1)
            val url = intent.extras!!.getString(Constants.PERSON_IMAGE_URL, "")

            supportActionBar!!.title = name

            imageLoader.loadImageBlurCenterCrop(url, person_profile_blurred, Constants.LARGE_IMAGE_SIZE)
            imageLoader.loadImageNoFormat(url, person_profile, Constants.LARGE_IMAGE_SIZE)

            viewModel.fetchPersonDetails(id, type)

            viewModel.personDetails.observe(this, Observer {
                person_age.text = "${it.age} years old"
                person_name.text = it.name
                person_pob.text = it.birthplace
                if (it.movieCount == 1) person_movies.text = "${it.movieCount} movie"
                else person_movies.text = "${it.movieCount} movies"

            })

            viewModel.fetchError.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
