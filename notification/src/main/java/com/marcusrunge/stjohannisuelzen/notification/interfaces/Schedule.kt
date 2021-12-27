package com.marcusrunge.stjohannisuelzen.notification.interfaces

import androidx.work.Configuration

interface Schedule {
    /**
     * Creates work manager configuration
     */
    fun createWorkManagerConfiguration(): Configuration

    /**
     * Creates and starts a recurring daily motto notification
     */
    fun startRecurringDailyMotto()

    /**
     * Stops a recurring daily motto notification
     */
    fun stopRecurringDailyMotto()
}