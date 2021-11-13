package com.marcusrunge.stjohannisuelzen.notification.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule

internal class NotificationImpl(context: Context?, dailyMotto: DailyMotto?) : NotificationBase() {
    init {
        _push = PushImpl.create(this)
        _schedule = ScheduleImpl.create(this)
    }

    override val push: Push
        get() = _push
    override val schedule: Schedule
        get() = _schedule
}