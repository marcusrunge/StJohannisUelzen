package com.marcusrunge.stjohannisuelzen.notification.implementations

import androidx.work.*
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule
import com.marcusrunge.stjohannisuelzen.notification.worker.DailyMottoNotificationWorker
import java.util.*
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
        val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        val delay = ((60 - minute) + (24 - hourOfDay) * 60 - 60).toLong()
        val request =
            PeriodicWorkRequestBuilder<DailyMottoNotificationWorker>(2, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .addTag("dailymotto")
                .build()
        WorkManager.getInstance(notificationBase.context!!).enqueueUniquePeriodicWork(
            "showDailyMotto",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    override fun stopRecurringDailyMotto() {
        WorkManager.getInstance(notificationBase.context!!).cancelAllWorkByTag(
            "dailymotto"
        )
    }
}