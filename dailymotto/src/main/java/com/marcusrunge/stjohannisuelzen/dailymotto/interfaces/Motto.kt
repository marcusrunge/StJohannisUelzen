package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Motto {
    fun get(date: Date): String?
}