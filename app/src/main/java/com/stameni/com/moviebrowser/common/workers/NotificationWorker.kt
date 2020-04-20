package com.stameni.com.moviebrowser.common.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.stameni.com.moviebrowser.common.libraries.NotificationManager

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        NotificationManager.makeStatusNotification(inputData, applicationContext)
        return Result.success()
    }
}