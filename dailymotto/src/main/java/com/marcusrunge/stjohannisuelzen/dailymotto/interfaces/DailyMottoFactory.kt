package com.marcusrunge.stjohannisuelzen.dailymotto.interfaces

import android.content.Context

interface DailyMottoFactory {
    fun create(context: Context?): DailyMotto
}