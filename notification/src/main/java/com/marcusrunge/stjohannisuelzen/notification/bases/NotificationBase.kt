package com.marcusrunge.stjohannisuelzen.notification.bases

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Toast

internal abstract class NotificationBase(
    internal val context: Context?,
    internal val dailyMotto: DailyMotto?,
    internal val newsFeed: NewsFeed?,
    internal val clazz: Class<*>?
) : Notification {
    protected lateinit var _push: Push
    protected lateinit var _toast: Toast
    protected lateinit var _schedule: Schedule
}