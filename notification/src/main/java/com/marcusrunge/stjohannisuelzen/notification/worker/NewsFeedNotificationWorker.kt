package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class NewsFeedNotificationWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val notificationBase: NotificationBase?
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
            val postId = sharedPreferences.getString("post_id", "")
            val post =
                notificationBase?.newsFeed?.content?.parseAsync("https://johannis-und-georg.wir-e.de")
            if (postId != post?.first) {
                notificationBase?.push?.showSmall(post?.second, post?.third, notificationBase.clazz)
            }
            sharedPreferences.edit().putString("post_id", post?.first).apply()
        }
        return Result.success()
    }
}