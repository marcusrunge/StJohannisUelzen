package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.notification.interfaces.NotificationFactory

class NotificationFactoryImpl {
    companion object : NotificationFactory {
        override fun create(
            context: Context?,
            dailyMotto: DailyMotto?,
            clazz: Class<*>?
        ): Notification = NotificationImpl(context, dailyMotto, clazz)
    }
}