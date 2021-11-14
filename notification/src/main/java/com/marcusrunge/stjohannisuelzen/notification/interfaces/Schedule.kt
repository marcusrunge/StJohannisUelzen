package com.marcusrunge.stjohannisuelzen.notification.interfaces

interface Schedule {
    /**
     * Creates and starts a recurring daily motto notification
     */
    fun startRecurringDailyMotto()
    /**
     * Stops a recurring daily motto notification
     */
    fun stopRecurringDailyMotto()
}