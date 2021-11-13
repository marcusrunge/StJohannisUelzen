package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.notification.interfaces.NotificationFactory

class NotificationFactoryImpl {
    companion object : NotificationFactory {
        private var notification: Notification? = null
        override fun create(context: Context?, dailyMotto: DailyMotto?): Notification = when {
            notification != null -> notification!!
            else -> {
                notification = NotificationImpl(context, dailyMotto)
                notification!!
            }
        }
    }
}