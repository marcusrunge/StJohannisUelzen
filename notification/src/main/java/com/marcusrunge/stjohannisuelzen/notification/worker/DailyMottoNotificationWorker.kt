package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

internal class DailyMottoNotificationWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}