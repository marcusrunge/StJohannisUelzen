package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import androidx.core.content.edit
import kotlinx.coroutines.runBlocking

internal class NewsFeedNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notificationBase: NotificationBase?
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        if (notificationBase?.newsFeed?.content == null) {
            return Result.failure()
        }
        return runBlocking {
            try {
                val post = notificationBase.newsFeed.content.parseAsync("https://johannis-und-georg.wir-e.de")
                if (post == null) {
                    return@runBlocking Result.success()
                }
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val lastPostId = sharedPreferences.getString("post_id", null)
                if (lastPostId != post.first) {
                    notificationBase.push.showSmall(post.second, post.third, notificationBase.clazz)
                    sharedPreferences.edit {
                        putString("post_id", post.first)
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}