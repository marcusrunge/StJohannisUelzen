package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.marcusrunge.stjohannisuelzen.notification.R
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push

internal class PushImpl(private val notificationBase: NotificationBase) : Push {
    private val CHANNEL_ID: String = "DM"

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

    override fun show(smallIcon: Int, textTitle: String?, textContent: String?) {
        var builder = NotificationCompat.Builder(notificationBase.context!!, CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createNotificationChannel() {
        val name = notificationBase.context?.getString(R.string.channel_name)
        val descriptionText = notificationBase.context?.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            notificationBase.context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}