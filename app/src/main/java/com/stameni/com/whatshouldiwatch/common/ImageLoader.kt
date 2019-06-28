package com.stameni.com.whatshouldiwatch.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pollux.widget.DualProgressView

class ImageLoader(
    private val preferences: SharedPreferences,
    private val context: Context
) {

    fun loadImageFromTmdb(url: String, view: ImageView, progressBar: DualProgressView?, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)

        if (loadImage) {
            if (view.visibility == View.GONE) view.visibility = View.VISIBLE
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/$size/$url")
                .centerCrop()
                .into(view)
        } else {
            if (progressBar != null) progressBar.visibility = View.GONE
        }
    }
}