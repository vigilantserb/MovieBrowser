package com.stameni.com.whatshouldiwatch.screens.singleActor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import kotlinx.android.synthetic.main.activity_single_actor.*
import kotlinx.android.synthetic.main.single_movie_actor_item.*

class SingleActorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_actor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewpager_main.adapter = SingleActorViewPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager_main)

        Glide.with(this)
            .load("https://www.myagecalculator.com/images/brad-pitt.jpg")
            .centerCrop()
            .into(person_image)
    }
}
