package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Inspiration {
    /**
     * Gets the advise from daily motto
     * @param date the date of the advise
     */
    suspend fun get(date: Date): Pair<String?, String?>?
}