package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Motto
import java.util.*

internal class MottoImpl(dailyMottoBase: DailyMottoBase) : Motto {
    companion object {
        private var motto: Motto? = null
        fun create(dailyMottoBase: DailyMottoBase): Motto = when {
            motto != null -> motto!!
            else -> {
                motto = MottoImpl(dailyMottoBase)
                motto!!
            }
        }
    }

    override fun get(date: Date): String? {
        TODO("Not yet implemented")
    }
}