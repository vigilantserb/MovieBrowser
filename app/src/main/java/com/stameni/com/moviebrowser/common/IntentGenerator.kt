package com.stameni.com.moviebrowser.common

import android.app.SearchManager
import android.content.Intent
import android.net.Uri

class IntentGenerator {
    fun sendTextMessageIntent(txtMsg: String): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, txtMsg)
            type = "text/plain"
        }
    }

    fun generateYoutubeTrailerIntent(youtubeKey: String): Intent {
        val url = "https://www.youtube.com/watch?v=$youtubeKey"
        return Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setPackage("com.google.android.youtube")
        }
    }

    fun generateGoogleNowIntent(query: String): Intent =
        Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
}