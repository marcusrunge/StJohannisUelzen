package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Motto {
    /**
     * Gets the motto from daily motto
     * @param date the date of the motto
     */
    fun get(date: Date): String?
}