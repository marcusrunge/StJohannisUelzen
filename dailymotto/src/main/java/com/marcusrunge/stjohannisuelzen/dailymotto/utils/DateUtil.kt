package com.marcusrunge.stjohannisuelzen.dailymotto.utils

import java.util.Calendar
import java.util.Date

internal class DateUtil {
    companion object {
        fun removeTime(date: Date): Date? {
            val cal: Calendar = Calendar.getInstance()
            cal.time = date
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.time
        }
    }
}