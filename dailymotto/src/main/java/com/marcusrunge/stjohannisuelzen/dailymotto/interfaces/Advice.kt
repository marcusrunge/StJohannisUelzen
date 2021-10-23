package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import java.util.*

interface Advice {
    fun get(date: Date): String?
}