package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import android.content.Context

interface DailyMottoFactory {
    /**
     * Creates the daily motto instance
     * @see DailyMotto
     * @param context The application context
     */
    fun create(context: Context?): DailyMotto
}