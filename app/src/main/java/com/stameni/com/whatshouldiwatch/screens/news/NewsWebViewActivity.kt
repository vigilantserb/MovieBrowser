package com.stameni.com.whatshouldiwatch.screens.news

import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.stameni.com.whatshouldiwatch.R
import kotlinx.android.synthetic.main.activity_news_web_view.*

class NewsWebViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web_view)
        if(intent.extras != null){
            val source = intent.extras!!.getString("source", "")

            web_view.loadUrl(source)

            // Get the web view settings instance
            val settings = web_view.settings

            // Enable java script in web view
            settings.javaScriptEnabled = true

            // Enable and setup web view cache
            settings.setAppCacheEnabled(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setAppCachePath(cacheDir.path)

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
            web_view.webViewClient = object: WebViewClient(){
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                }

                override fun onPageFinished(view: WebView, url: String) {
                }
            }

            web_view.webChromeClient = object: WebChromeClient(){
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    gif_progress_bar.progress = newProgress
                    if(newProgress == 100) gif_progress_bar.visibility = View.GONE
                }
            }
        }
    }


}
