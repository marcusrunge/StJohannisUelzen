package com.marcusrunge.stjohannisuelzen.notification.implementations

import com.marcusrunge.stjohannisuelzen.notification.bases.NotificationBase
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Toast

internal class ToastImpl(private val notificationBase: NotificationBase) : Toast {
    companion object {
        private var toast: Toast? = null
        fun create(notificationBase: NotificationBase): Toast = when {
            toast != null -> toast!!
            else -> {
                toast = ToastImpl(notificationBase)
                toast!!
            }
        }
    }

    override fun showShort(message: String?) {
        android.widget.Toast.makeText(
            notificationBase.context,
            message,
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    override fun showLong(message: String?) {
        android.widget.Toast.makeText(
            notificationBase.context,
            message,
            android.widget.Toast.LENGTH_LONG
        ).show()
    }
}