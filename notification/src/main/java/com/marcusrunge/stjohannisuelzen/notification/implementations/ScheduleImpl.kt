package com.marcusrunge.stjohannisuelzen.notification.implementations

import androidx.work.*
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule
import com.marcusrunge.stjohannisuelzen.notification.worker.DailyMottoNotificationWorker
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
            .setWorkerFactory(DailyMottoNotificationWorkerFactory(notificationBase))
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
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .addTag("dailymotto")
                .build()
        WorkManager.getInstance(notificationBase.context!!).enqueueUniquePeriodicWork(
            "showDailyMotto",
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    override fun stopRecurringDailyMotto() {
        WorkManager.getInstance(notificationBase.context!!).cancelAllWorkByTag(
            "dailymotto"
        )
    }
}