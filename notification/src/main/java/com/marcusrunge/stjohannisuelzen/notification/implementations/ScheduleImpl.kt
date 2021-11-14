package com.marcusrunge.stjohannisuelzen.notification.implementations

import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule

internal class ScheduleImpl(notificationBase: NotificationBase) : Schedule {
    companion object {
        private var schedule: Schedule? = null
        fun create(notificationBase: NotificationBase): Schedule = when {
            schedule != null -> schedule!!
            else -> {
                schedule = ScheduleImpl(notificationBase)
                schedule!!
            }
        }
    }

    override fun startRecurringDailyMotto() {
        TODO("Not yet implemented")
    }

    override fun stopRecurringDailyMotto() {
        TODO("Not yet implemented")
    }
}