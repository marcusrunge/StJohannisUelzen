package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Toast

internal class NotificationImpl(
    context: Context?,
    dailyMotto: DailyMotto?,
    newsFeed: NewsFeed?,
    clazz: Class<*>?
) :
    NotificationBase(context, dailyMotto, newsFeed, clazz) {
    init {
        _push = PushImpl.create(this)
        _toast = ToastImpl.create(this)
        _schedule = ScheduleImpl.create(this)
    }

    override val push: Push
        get() = _push
    override val toast: Toast
        get() = _toast
    override val schedule: Schedule
        get() = _schedule
}