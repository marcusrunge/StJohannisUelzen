package com.marcusrunge.stjohannisuelzen.notification.implementations

import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule
import com.marcusrunge.stjohannisuelzen.notification.worker.DailyMottoNotificationWorker
import com.marcusrunge.stjohannisuelzen.notification.worker.NewsFeedNotificationWorker
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

internal class ScheduleImpl(private val notificationBase: NotificationBase) : Schedule {
    companion object {
        private var schedule: Schedule? = null
        fun create(notificationBase: NotificationBase): Schedule = when {
            schedule != null -> schedule!!
            else -> {
                schedule = ScheduleImpl(notificationBase)
                schedule!!
            }
        }
    }

    override fun createWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(NotificationWorkerFactory(notificationBase))
            .build()
    }

    override fun startRecurringDailyMotto() {
        stopRecurringDailyMotto()
        val now = LocalDateTime.now()
        val initialDelay = (1441 - ((now.hour * 60) + now.minute)).toLong()
        val request =
            PeriodicWorkRequestBuilder<DailyMottoNotificationWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .addTag("dailymotto")
                .build()
        WorkManager.getInstance(notificationBase.context!!).enqueueUniquePeriodicWork(
            "showDailyMotto",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request
        )
    }

    override fun stopRecurringDailyMotto() {
        WorkManager.getInstance(notificationBase.context!!).cancelAllWorkByTag(
            "dailymotto"
        )
    }

    override fun startRecurringNewsFeedNotification() {
        // 1. Remove the explicit stop call if it's redundant, and safely handle the context
        val context = notificationBase?.context ?: return

        // 2. Add Network Constraints (Crucial for a network-backed worker)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 3. Build the request with constraints and backoff policy
        val request = PeriodicWorkRequestBuilder<NewsFeedNotificationWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS, // 10 seconds default minimum
                TimeUnit.MILLISECONDS
            )
            .addTag("newsfeed")
            .build()

        // 4. Enqueue using KEEP policy to avoid resetting the 15-minute execution clock
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "showNews",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    override fun stopRecurringNewsFeedNotification() {
        WorkManager.getInstance(notificationBase.context!!).cancelAllWorkByTag(
            "newsfeed"
        )
    }
}