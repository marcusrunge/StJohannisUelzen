package com.marcusrunge.stjohannisuelzen.notification.worker

import android.content.Context
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import java.io.IOException

internal class NewsFeedNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notificationBase: NotificationBase?
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        // 1. Validate dependencies early
        val feedContent = notificationBase?.newsFeed?.content
            ?: return Result.failure()

        return try {
            // 2. Execute network fetch (switches to IO dispatcher automatically if handled inside)
            val latestPost = feedContent.getLatestBlogEntry("https://johannis-und-georg.wir-e.de")
                ?: return Result.success() // Page loaded perfectly, but no posts found at all.

            // 3. Open namespaced SharedPreferences
            val sharedPreferences =
                applicationContext.getSharedPreferences("blog_worker_prefs", Context.MODE_PRIVATE)
            val lastPostId = sharedPreferences.getString("last_post_id", null)

            // 4. Evaluate and dispatch notification
            if (lastPostId != latestPost.id) {
                notificationBase.push.showSmall(
                    latestPost.title,
                    latestPost.excerpt,
                    notificationBase.clazz
                )

                // 5. Use synchronous commit to prevent data loss on immediate worker termination
                sharedPreferences.edit(commit = true) {
                    putString("last_post_id", latestPost.id)
                }
            }

            Result.success()
        } catch (e: IOException) {
            // Captures connectivity drops, timeouts, and server 500s.
            // WorkManager will automatically schedule a retry based on your backoff policy.
            Result.retry()
        } catch (e: Exception) {
            // Captures code bugs, null pointers, or severe HTML structural parsing bugs.
            // Fail fast so we don't loop endlessly retrying bad code logic.
            Result.failure()
        }
    }
}