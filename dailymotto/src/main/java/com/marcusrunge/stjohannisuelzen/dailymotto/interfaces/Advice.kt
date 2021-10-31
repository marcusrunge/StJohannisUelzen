package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Advice {
    /**
     * Gets the advise from daily motto
     * @param date the date of the advise
     */
    fun get(date: Date): String?
}