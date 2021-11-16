package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.notification.worker.DailyMottoNotificationWorker

internal class DailyMottoNotificationWorkerFactory(private val dailyMotto: DailyMotto?): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return DailyMottoNotificationWorker(appContext, workerParameters, dailyMotto)
    }
}