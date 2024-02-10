package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.worker.DailyMottoNotificationWorker
import com.marcusrunge.stjohannisuelzen.notification.worker.NewsFeedNotificationWorker

internal class NotificationWorkerFactory(private val notificationBase: NotificationBase?) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DailyMottoNotificationWorker::class.java.name -> DailyMottoNotificationWorker(
                appContext,
                workerParameters,
                notificationBase
            )

            NewsFeedNotificationWorker::class.java.name -> NewsFeedNotificationWorker(
                appContext,
                workerParameters,
                notificationBase
            )

            else -> null
        }
    }
}