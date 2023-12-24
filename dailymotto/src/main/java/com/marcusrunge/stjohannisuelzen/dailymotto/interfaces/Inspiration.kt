package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.Date

interface Inspiration {
    /**
     * Gets the advise from daily motto
     * @param date the date of the advise
     */
    suspend fun getAsync(date: Date): Pair<String?, String?>?
}