package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Advice
import java.util.*

internal class AdviceImpl(dailyMottoBase: DailyMottoBase) : Advice {
    companion object {
        private var advise: Advice? = null
        fun create(dailyMottoBase: DailyMottoBase): Advice = when {
            advise != null -> advise!!
            else -> {
                advise = AdviceImpl(dailyMottoBase)
                advise!!
            }
        }
    }

    override fun get(date: Date): String? {
        TODO("Not yet implemented")
    }
}