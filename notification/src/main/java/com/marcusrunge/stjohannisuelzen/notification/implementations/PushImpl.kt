package com.marcusrunge.stjohannisuelzen.notification.implementations

import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Push

internal class PushImpl(notificationBase: NotificationBase) : Push {
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

    override fun show(message: String?) {
        TODO("Not yet implemented")
    }
}