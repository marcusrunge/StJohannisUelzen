package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.marcusrunge.stjohannisuelzen.notification.R
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push

internal class PushImpl(private val notificationBase: NotificationBase) : Push {
    private val CHANNEL_ID: String = "SJUE"
    private var notificationId: Int = 0

    companion object {
        private var push: Push? = null
        fun create(notificationBase: NotificationBase): Push = when {
            push != null -> push!!
            else -> {
                push = PushImpl(notificationBase)
                push!!
            }
        }
    }

    init {
        createNotificationChannel()
    }

    override fun showSmall(textTitle: String?, textContent: String?, clazz: Class<*>?) {
        val builder = createNotificationBuilder(textTitle, clazz ?: notificationBase.clazz)
        builder.setContentText(textContent)
        notifyNotification(builder.build())
    }

    override fun showLarge(
        textTitle: String?,
        textLine1: String?,
        textLine2: String?,
        clazz: Class<*>?
    ) {
        val builder = createNotificationBuilder(textTitle, clazz ?: notificationBase.clazz)
        builder
            .setContentText(textLine1)
            .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(textLine1 + "\n\n" + textLine2)
        )
        notifyNotification(builder.build())
    }

    private fun createNotificationChannel() {
        val name = notificationBase.context?.getString(R.string.channel_name)
        val descriptionText = notificationBase.context?.getString(R.string.channel_description)
        val channel =
            NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
        val notificationManager: NotificationManager =
            notificationBase.context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder(
        textTitle: String?,
        clazz: Class<*>?
    ): NotificationCompat.Builder {
        NotificationManagerCompat.from(notificationBase.context!!).cancel(notificationId)
        val intent = Intent(notificationBase.context, clazz)/*.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(notificationBase.context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        return NotificationCompat.Builder(notificationBase.context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_facetcross)
            .setContentTitle(textTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
    }

    private fun notifyNotification(notification: Notification) {
        NotificationManagerCompat.from(notificationBase.context!!).cancel(notificationId)
        with(NotificationManagerCompat.from(notificationBase.context)) {
            notificationId++
            notify(notificationId, notification)
        }
    }
}