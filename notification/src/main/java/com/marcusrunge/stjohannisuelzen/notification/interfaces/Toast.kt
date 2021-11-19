package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Toast {
    /**
     * Shows a short toast message
     * @param message The toast message
     */
    fun showShort(message: String?)

    /**
     * Shows a long toast message
     * @param message The toast message
     */
    fun showLong(message: String?)
}