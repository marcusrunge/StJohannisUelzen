package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.worker.NewsFeedNotificationWorker

internal class NewsFeedNotificationWorkerFactory(private val notificationBase: NotificationBase?) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return NewsFeedNotificationWorker(appContext, workerParameters, notificationBase)
    }
}