package com.marcusrunge.stjohannisuelzen.notification.interfaces

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto

interface NotificationFactory {
    /**
     * Creates the daily notification instance
     * @see Notification
     * @param context The application context
     * @param dailyMotto The daily motto instance
     */
    fun create(context: Context?, dailyMotto: DailyMotto?): Notification
}