package com.stameni.com.moviebrowser.common

import android.app.SearchManager
import android.content.Intent
import android.net.Uri

class IntentGenerator {
    companion object {
        fun generateYoutubeTrailerIntent(youtubeKey: String): Intent {
            val url = "https://www.youtube.com/watch?v=$youtubeKey"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")
            return intent
        }

        fun generateGoogleNowIntent(query: String): Intent =
            Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, query)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            }
    }
}