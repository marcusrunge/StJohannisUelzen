package com.marcusrunge.stjohannisuelzen.notification.interfaces

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed

interface NotificationFactory {
    /**
     * Creates the daily notification instance
     * @see Notification
     * @param context The application context
     * @param dailyMotto The daily motto instance
     * @param clazz The main activity class
     */
    fun create(
        context: Context?,
        dailyMotto: DailyMotto?,
        newsFeed: NewsFeed?,
        clazz: Class<*>?
    ): Notification
}