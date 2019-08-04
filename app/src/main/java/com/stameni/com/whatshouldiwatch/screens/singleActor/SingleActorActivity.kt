package com.stameni.com.whatshouldiwatch.screens.singleActor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseActivity
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_single_actor.*
import kotlinx.android.synthetic.main.single_movie_actor_item.*
import javax.inject.Inject

class SingleActorActivity : BaseActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getControllerComponent().inject(this)
        setContentView(R.layout.activity_single_actor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpager_main.adapter = SingleActorViewPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager_main)

        if(intent.extras != null){
            val name = intent.extras!!.getString("actorName", "")
            val id = intent.extras!!.getInt("actorId", 0)
            val url = intent.extras!!.getString("actorUrl", "")

            supportActionBar!!.title = name

            imageLoader.loadImageBlurCenterCrop(url, person_profile_blurred, "w500")
            imageLoader.loadImageNoFormat(url, person_profile, "w500")


        }
    }
}
