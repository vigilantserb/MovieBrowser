package com.stameni.com.moviebrowser.common.libraries

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.*
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.workers.NotificationWorker
import com.stameni.com.moviebrowser.screens.MainActivity
import java.util.concurrent.TimeUnit

object NotificationManager {
    private val VERBOSE_NOTIFICATION_CHANNEL_NAME = "MovieBrowser"
    private val CHANNEL_ID = "123"
    private val NOTIFICATION_ID = 1234

    val KEY_NOTIFICATION_MESSAGE = "MESSAGE"
    val KEY_NOTIFICATION_TITLE = "TITLE"
    val IS_PERIODIC = "IS_PERIODIC"

    fun makeStatusNotification(data: Data, context: Context) {
        val title = data.getString("TITLE")
        val description = data.getString("MESSAGE")

        if(title == null || description == null)
            return

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        // Create an Intent for the activity you want to start
        val resultIntent = Intent(context, MainActivity::class.java)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(resultPendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

    fun greetingNotification(workManager: WorkManager){
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

    private fun setupReacurringNotificationWorker(workManager: WorkManager) {
        val dailyNotif = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(createInputDataMessage("This is daily notif", "this is daily notif"))
            .build()

        workManager.enqueueUniquePeriodicWork("uniq", ExistingPeriodicWorkPolicy.KEEP, dailyNotif)
    }

    private fun createInputDataMessage(message: String, title: String): Data =
        Data.Builder()
            .putString(KEY_NOTIFICATION_MESSAGE, message)
            .putString(KEY_NOTIFICATION_TITLE, title)
            .putBoolean(IS_PERIODIC, true)
            .build()
}