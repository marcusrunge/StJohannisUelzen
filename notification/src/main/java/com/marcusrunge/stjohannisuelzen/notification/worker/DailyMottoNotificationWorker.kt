package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto

internal class DailyMottoNotificationWorker(appContext: Context, workerParams: WorkerParameters, private val dailyMotto: DailyMotto?) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}