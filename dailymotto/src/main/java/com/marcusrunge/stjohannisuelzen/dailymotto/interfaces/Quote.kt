package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Quote {
    /**
     * Gets the motto from daily motto
     * @param date the date of the motto
     */
    suspend fun getAsync(date: Date): Pair<String?, String?>?
}