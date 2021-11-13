package com.marcusrunge.stjohannisuelzen.notification.bases

import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Schedule

internal abstract class NotificationBase : Notification {
    protected lateinit var _push: Push
    protected lateinit var _schedule: Schedule
}