package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Inspiration
import com.marcusrunge.stjohannisuelzen.dailymotto.utils.DateUtil
import com.marcusrunge.stjohannisuelzen.dailymotto.utils.StringUtil.Companion.normalize
import java.text.SimpleDateFormat
import java.util.*

internal class InspirationImpl(private val dailyMottoBase: DailyMottoBase) : Inspiration {
    companion object {
        private var inspiration: Inspiration? = null
        fun create(dailyMottoBase: DailyMottoBase): Inspiration = when {
            inspiration != null -> inspiration!!
            else -> {
                inspiration = InspirationImpl(dailyMottoBase)
                inspiration!!
            }
        }
    }

    override suspend fun getAsync(date: Date): Pair<String?, String?>? {
        val dailyMottos = dailyMottoBase.dailyMottos.await()
        dailyMottos.forEach {
            val itDate: Date? = SimpleDateFormat("yyyy-MM-dd").parse(it?.Datum)
            if (itDate == DateUtil.removeTime(date))
                return Pair(it?.Lehrtext?.normalize(), it?.Lehrtextvers)
        }
        return null
    }
}