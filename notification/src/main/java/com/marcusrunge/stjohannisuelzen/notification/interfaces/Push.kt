package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Push {
    /**
     * Shows a notification message
     * @param message The notification message
     */
    fun show(message:String?)
}