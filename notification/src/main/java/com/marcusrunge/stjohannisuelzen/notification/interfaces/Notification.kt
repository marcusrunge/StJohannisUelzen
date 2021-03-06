package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Notification {
    /**
     * Gets the push instance
     * @see Push
     */
    val push: Push

    /**
     * Gets the toast instance
     * @see Toast
     */
    val toast: Toast

    /**
     * Gets the schedule instance
     * @see Schedule
     */
    val schedule: Schedule
}