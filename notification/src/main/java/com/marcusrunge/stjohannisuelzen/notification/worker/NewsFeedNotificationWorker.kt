package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase

internal class NewsFeedNotificationWorker (appContext: Context,
                                           workerParams: WorkerParameters,
                                           private val notificationBase: NotificationBase?
) : Worker(appContext, workerParams){
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}