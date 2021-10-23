package com.marcusrunge.stjohannisuelzen.dailymotto.bases

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.R

import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Advice
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Motto


internal abstract class DailyMottoBase(internal val context: Context?) : DailyMotto {
    protected lateinit var _advice: Advice
    protected lateinit var _motto: Motto

    init {
        //Test only
        loadDailyMottos()
    }

    private fun loadDailyMottos(){
        val parser = context?.resources?.getXml(R.xml.dailymottos)
    }
}