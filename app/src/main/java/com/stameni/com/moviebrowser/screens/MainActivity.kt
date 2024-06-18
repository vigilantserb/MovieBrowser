package com.stameni.com.moviebrowser.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.common.workers.NotificationWorker
import com.stameni.com.moviebrowser.databinding.MainActivityBinding
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainActivityBinding>(MainActivityBinding::inflate) {
    private val workManager = WorkManager.getInstance(application)
    val KEY_NOTIFICATION_MESSAGE = "MESSAGE"
    val KEY_NOTIFICATION_TITLE = "TITLE"

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
        setupGreetingNotificationWorker(workManager)
    }

    override fun setupViews() {
        bottomNav = binding.bottomNav
    }

    private fun setupGreetingNotificationWorker(workManager: WorkManager) {
        val blurRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(
                createInputDataMessage(
                    "Hello! Glad to see you're using Movie Browser. " +
                            "The code for this application is open-source and you're welcome to contribute! " +
                            "You can find the link to the github project in the settings page",
                    "Greetings!"
                )
            )
            .build()

        workManager.enqueueUniquePeriodicWork("greeting notification", ExistingPeriodicWorkPolicy.KEEP, blurRequest)
    }

    private fun createInputDataMessage(message: String, title: String): Data =
        Data.Builder()
            .putString(KEY_NOTIFICATION_MESSAGE, message)
            .putString(KEY_NOTIFICATION_TITLE, title)
            .build()

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }
}
