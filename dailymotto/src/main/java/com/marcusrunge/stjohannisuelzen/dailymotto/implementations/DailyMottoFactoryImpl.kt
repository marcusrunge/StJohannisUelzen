package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMottoFactory

class DailyMottoFactoryImpl {
    companion object : DailyMottoFactory {
        private var dailyMotto: DailyMottoImpl? = null
        override fun create(context: Context?): DailyMotto = when {
            dailyMotto != null -> dailyMotto!!
            else -> {
                dailyMotto = DailyMottoImpl(context)
                dailyMotto!!
            }
        }
    }
}