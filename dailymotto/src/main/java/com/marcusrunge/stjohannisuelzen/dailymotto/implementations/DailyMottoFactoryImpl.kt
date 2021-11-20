package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMottoFactory

class DailyMottoFactoryImpl {
    companion object : DailyMottoFactory {
        override fun create(context: Context?): DailyMotto = DailyMottoImpl(context)
    }
}