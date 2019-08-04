package com.stameni.com.whatshouldiwatch.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.pollux.widget.DualProgressView
import com.stameni.com.whatshouldiwatch.R
import jp.wasabeef.glide.transformations.BlurTransformation

class ImageLoader(
    private val preferences: SharedPreferences,
    private val context: Context
) {

    fun loadImageFromTmdb(url: String, view: ImageView, progressBar: DualProgressView?, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            if (view.visibility == View.GONE)
                view.visibility = View.VISIBLE
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view)
        } else {
            if (progressBar != null) progressBar.visibility = View.GONE
        }
    }
    fun loadImageFromTmdbNoFormat(url: String, view: ImageView, progressBar: DualProgressView?, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)

        if (loadImage) {
            if (view.visibility == View.GONE) view.visibility = View.VISIBLE
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/$size/$url")
                .into(view)
        } else {
            if (progressBar != null) progressBar.visibility = View.GONE
        }
    }

    fun loadListImageCenterCrop(url: String, view: ImageView, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.list_placeholder)
                .centerCrop()
                .into(view)
        }
    }

    fun loadNewsImageCenterCrop(url: String, view: ImageView) {
        val loadImage = preferences.getBoolean("loadImage", true)

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.list_placeholder)
                .centerCrop()
                .into(view)
        }
    }

    fun loadPosterImageCenterCrop(url: String, view: ImageView, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.poster_placeholder)
                .centerCrop()
                .into(view)
        }
    }

    fun loadPosterImageFitCenter(url: String, view: ImageView, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .fitCenter()
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.poster_placeholder)
                .fitCenter()
                .into(view)
        }
    }

    fun loadImageNoFormat(url: String, view: ImageView, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.list_placeholder)
                .into(view)
        }
    }

    fun loadImageBlurCenterCrop(url: String, view: ImageView, size: String) {
        val loadImage = preferences.getBoolean("loadImage", true)
        val url = "https://image.tmdb.org/t/p/$size/$url"

        if (loadImage) {
            Glide.with(context)
                .load(url)
                .apply(bitmapTransform(BlurTransformation(50)))
                .into(view)
        }else{
            Glide.with(context)
                .load(R.drawable.list_placeholder)
                .apply(bitmapTransform(BlurTransformation(50)))
                .into(view)
        }
    }
}