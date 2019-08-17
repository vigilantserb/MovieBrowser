package com.stameni.com.whatshouldiwatch.common

import android.content.Intent
import android.net.Uri

class LinkGenerator {
    companion object {
        fun generateYoutubeTrailerIntent(youtubeKey: String): Intent {
            val url = "https://www.youtube.com/watch?v=$youtubeKey"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")
            return intent
        }
    }
}