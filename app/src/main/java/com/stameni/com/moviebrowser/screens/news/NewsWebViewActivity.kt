package com.stameni.com.moviebrowser.screens.news

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.pollux.widget.DualProgressView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.databinding.ActivityNewsWebViewBinding

class NewsWebViewActivity :
    BaseActivity<ActivityNewsWebViewBinding>(ActivityNewsWebViewBinding::inflate) {

    private lateinit var web_view: WebView
    private lateinit var gif_progress_bar: ProgressBar

    override fun setupViews() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            val source = intent.extras!!.getString(Constants.SOURCE_LINK, "")

            web_view.loadUrl(source)

            // Get the web view settings instance
            val settings = web_view.settings

            // Enable java script in web view
            settings.javaScriptEnabled = true

            // Enable and setup web view cache
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            // Enable zooming in web view
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true

            // Zoom web view text
            settings.textZoom = 125

            // Enable disable images in web view
            settings.blockNetworkImage = false
            // Whether the WebView should load image resources
            settings.loadsImagesAutomatically = true

            // More web view settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true  // api 26
            }

            //settings.pluginState = WebSettings.PluginState.ON
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false

            // WebView settings
            web_view.fitsSystemWindows = true

            web_view.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            // Set web view client
            web_view.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                }

                override fun onPageFinished(view: WebView, url: String) {
                }
            }

            web_view.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    gif_progress_bar.progress = newProgress
                    if (newProgress == 100) gif_progress_bar.visibility = View.GONE
                }
            }
        }
    }
}
