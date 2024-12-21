package com.marcusrunge.stjohannisuelzen.dailymotto.bases

import android.content.Context
import android.content.res.XmlResourceParser
import com.marcusrunge.stjohannisuelzen.dailymotto.R
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Inspiration
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Quote
import com.marcusrunge.stjohannisuelzen.dailymotto.models.Losungen
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.xmlpull.v1.XmlPullParser
import java.util.Calendar

internal abstract class DailyMottoBase(internal val context: Context?) : DailyMotto {
    protected lateinit var _inspiration: Inspiration
    protected lateinit var _quote: Quote

    internal val dailyMottos: Deferred<MutableList<Losungen?>> = loadDailyMottos()

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadDailyMottos() =
        GlobalScope.async(Dispatchers.IO) {
            var parser: XmlResourceParser? = null
            when (Calendar.getInstance().get(Calendar.YEAR)) {
                2025 -> {
                    parser = context?.resources?.getXml(R.xml.losungen2025)
                }

                2024 -> {
                    parser = context?.resources?.getXml(R.xml.losungen2024)
                }
            }
            val result = mutableListOf<Losungen?>()
            if (parser != null) {
                var eventType = parser.eventType
                var losungen: Losungen? = null
                var text: String? = null
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    val tagName = parser.name
                    when (eventType) {
                        XmlPullParser.START_TAG -> if (tagName.equals("Losungen")) {
                            losungen = Losungen()
                        }

                        XmlPullParser.TEXT -> text = parser.text
                        XmlPullParser.END_TAG -> {
                            when (tagName) {
                                "Losungen" -> result.add(losungen)
                                "Datum" -> losungen?.Datum = text
                                "Wtag" -> losungen?.Wtag = text
                                "Sonntag" -> losungen?.Sonntag = text
                                "Losungstext" -> losungen?.Losungstext = text
                                "Losungsvers" -> losungen?.Losungsvers = text
                                "Lehrtext" -> losungen?.Lehrtext = text
                                "Lehrtextvers" -> losungen?.Lehrtextvers = text
                            }
                        }
                    }
                    eventType = parser.next()
                }
            }
            parser?.close()
            return@async result
        }
}