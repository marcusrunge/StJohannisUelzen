package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

internal class DailyMottoNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notificationBase: NotificationBase?
) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val time = Calendar.getInstance().time
            val first = notificationBase?.dailyMotto?.quote?.getAsync(time)
            val second =
                notificationBase?.dailyMotto?.inspiration?.getAsync(Calendar.getInstance().time)
            (notificationBase as Notification).push.showLarge(
                "Tageslosung",
                first?.first + "\n" + first?.second,
                second?.first + "\n" + second?.second,
                notificationBase.clazz
            )
        }
        return Result.success()
    }
}