package com.stameni.com.moviebrowser.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.common.workers.NotificationWorker
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity() {
    private val workManager = WorkManager.getInstance(application)
    val KEY_NOTIFICATION_MESSAGE = "MESSAGE"
    val KEY_NOTIFICATION_TITLE = "TITLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
        setupGreetingNotificationWorker(workManager)
    }

    private fun setupGreetingNotificationWorker(workManager: WorkManager) {
        val blurRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(
                createInputDataMessage(
                    "Hello! Glad to see you're using Movie Browser. " +
                            "The code for this application is open-source and you're welcome to contribute! " +
                            "You can find the link to the github project in the settings page",
                    "Greetings!"
                )
            )
            .build()

        workManager.enqueue(blurRequest)
    }

    private fun createInputDataMessage(message: String, title: String): Data =
        Data.Builder()
            .putString(KEY_NOTIFICATION_MESSAGE, message)
            .putString(KEY_NOTIFICATION_TITLE, title)
            .build()

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }
}
