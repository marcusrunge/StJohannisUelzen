package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Inspiration
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Quote

internal class DailyMottoImpl(context: Context?) : DailyMottoBase(context) {
    init {
        _inspiration = InspirationImpl.create(this)
        _quote = QuoteImpl.create(this)
    }

    override val inspiration: Inspiration
        get() = _inspiration
    override val quote: Quote
        get() = _quote
}