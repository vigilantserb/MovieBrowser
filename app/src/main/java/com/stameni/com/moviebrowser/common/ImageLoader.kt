package com.stameni.com.moviebrowser.common

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.pollux.widget.DualProgressView
import com.stameni.com.moviebrowser.R
import jp.wasabeef.glide.transformations.BlurTransformation

class ImageLoader(
    private val preferences: SharedPreferences,
    private val context: Context
) {

    fun loadImageFromTmdb(
        url: String,
        view: ImageView,
        progressBar: DualProgressView?,
        size: String
    ) {
        if (!shouldLoadImage()) {
            progressBar?.visibility = View.GONE
            return
        }

        showView(view)
        loadImageIntoView(buildTmdbUrl(url, size), view, centerCrop = true)
    }

    fun loadListImageCenterCrop(url: String, view: ImageView, size: String) {
        if (!shouldLoadImage()) {
            loadPlaceholderIntoView(view)
            return
        }

        loadImageIntoView(buildTmdbUrl(url, size), view, centerCrop = true)
    }

    fun loadNewsImageCenterCrop(url: String, view: ImageView) {
        if (!shouldLoadImage()) {
            loadPlaceholderIntoView(view)
            return
        }

        loadImageIntoView(url, view, centerCrop = true)
    }

    fun loadPosterImageCenterCrop(url: String, view: ImageView, size: String) {
        if (!shouldLoadImage()) {
            loadPlaceholderIntoView(view)
            return
        }

        loadImageIntoView(buildTmdbUrl(url, size), view, centerCrop = true)
    }

    fun loadImageNoFormat(url: String, view: ImageView, size: String) {
        if (!shouldLoadImage()) {
            loadPlaceholderIntoView(view, R.drawable.list_placeholder)
            return
        }

        loadImageIntoView(buildTmdbUrl(url, size), view, centerCrop = false)
    }

    fun loadImageBlurCenterCrop(url: String, view: ImageView, size: String) {
        if (!shouldLoadImage()) {
            loadPlaceholderIntoView(view, R.drawable.list_placeholder, blur = true)
            return
        }

        loadImageIntoView(buildTmdbUrl(url, size), view, centerCrop = true, blur = true)
    }

    private fun buildTmdbUrl(url: String, size: String) = "https://image.tmdb.org/t/p/$size/$url"

    private fun shouldLoadImage() = preferences.getBoolean("loadImage", true)

    private fun showView(view: ImageView) {
        if (view.visibility == View.GONE) view.visibility = View.VISIBLE
    }

    private fun loadImageIntoView(
        url: String,
        view: ImageView,
        centerCrop: Boolean = false,
        blur: Boolean = false
    ) {
        val glideRequest = Glide.with(context).load(url)
        if (centerCrop) glideRequest.centerCrop()
        if (blur) glideRequest.apply(bitmapTransform(BlurTransformation(50)))
        glideRequest.into(view)
    }

    private fun loadPlaceholderIntoView(
        view: ImageView,
        placeholderResId: Int = R.drawable.poster_placeholder,
        blur: Boolean = false
    ) {
        val glideRequest = Glide.with(context).load(placeholderResId)
        if (blur) glideRequest.apply(bitmapTransform(BlurTransformation(50)))
        glideRequest.centerCrop().into(view)
    }
}