package com.stameni.com.moviebrowser.screens.singlePerson

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.databinding.ActivitySinglePersonBinding
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class SinglePersonActivity :
    BaseActivity<ActivitySinglePersonBinding>(ActivitySinglePersonBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SinglePersonActivityViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var viewpagerMain: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var personProfileBlurred: ImageView
    private lateinit var personProfile: CircleImageView
    private lateinit var personAge: TextView
    private lateinit var personName: TextView
    private lateinit var personPob: TextView
    private lateinit var personMovies: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpagerMain.adapter = SinglePersonViewPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpagerMain)

        if (intent.extras != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SinglePersonActivityViewModel::class.java)

            val type = intent.extras!!.getString(Constants.PERSON_TYPE, "")
            val name = intent.extras!!.getString(Constants.PERSON_NAME, "")
            val id = intent.extras!!.getInt(Constants.PERSON_ID, -1)
            val url = intent.extras!!.getString(Constants.PERSON_IMAGE_URL, "")

            supportActionBar!!.title = name

            imageLoader.loadImageBlurCenterCrop(
                url,
                personProfileBlurred,
                Constants.LARGE_IMAGE_SIZE
            )
            imageLoader.loadImageNoFormat(url, personProfile, Constants.LARGE_IMAGE_SIZE)

            viewModel.fetchPersonDetails(id, type)

            viewModel.personDetails.observe(this, Observer {
                personAge.text = "${it.age} years old"
                personName.text = it.name
                personPob.text = it.birthplace
                if (it.movieCount == 1) personMovies.text = "${it.movieCount} movie"
                else personMovies.text = "${it.movieCount} movies"

            })

            viewModel.fetchError.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun setupViews() {
        toolbar = binding.toolbar
        viewpagerMain = binding.viewpagerMain
        tabs = binding.tabs
        personProfile = binding.personProfile
        personProfileBlurred = binding.personProfileBlurred
        personAge = binding.personAge
        personMovies = binding.personMovies
        personPob = binding.personPob
        personName = binding.personName
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
