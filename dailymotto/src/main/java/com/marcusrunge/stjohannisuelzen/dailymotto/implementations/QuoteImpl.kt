package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Quote
import com.marcusrunge.stjohannisuelzen.dailymotto.utils.DateUtil
import java.text.SimpleDateFormat
import java.util.*

internal class QuoteImpl(private val dailyMottoBase: DailyMottoBase) : Quote {
    companion object {
        private var quote: Quote? = null
        fun create(dailyMottoBase: DailyMottoBase): Quote = when {
            quote != null -> quote!!
            else -> {
                quote = QuoteImpl(dailyMottoBase)
                quote!!
            }
        }
    }

    override suspend fun get(date: Date): Pair<String?, String?>? {
        val dailyMottos = dailyMottoBase.dailyMottos.await()
        dailyMottos.forEach {
            val itDate: Date = SimpleDateFormat("yyyy-MM-dd").parse(it?.Datum)
            if (itDate == DateUtil.removeTime(date))
                return Pair(it?.Losungstext, it?.Losungsvers)
        }
        return null
    }
}