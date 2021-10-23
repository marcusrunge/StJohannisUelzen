package com.marcusrunge.stjohannisuelzen.dailymotto.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.bases.DailyMottoBase
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Advice
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Motto

internal class DailyMottoImpl(context: Context?):DailyMottoBase(context) {
    init {
        _advice=AdviceImpl.create(this)
        _motto=MottoImpl.create(this)
    }

    override val advice: Advice
        get() = _advice
    override val motto: Motto
        get() = _motto
}