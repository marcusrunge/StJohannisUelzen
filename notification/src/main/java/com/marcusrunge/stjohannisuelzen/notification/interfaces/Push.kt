package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Push {
    /**
     * Shows a notification message
     * @param smallIcon The notification small icon
     * @param textTitle The notification title
     * @param textContent The notification content
     */
    fun show(smallIcon: Int, textTitle: String?, textContent: String?)
}