package com.marcusrunge.stjohannisuelzen.dailymotto.bases

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.R

import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Advice
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.Motto
import com.marcusrunge.stjohannisuelzen.dailymotto.models.Losungen
import org.xmlpull.v1.XmlPullParser


internal abstract class DailyMottoBase(internal val context: Context?) : DailyMotto {
    protected lateinit var _advice: Advice
    protected lateinit var _motto: Motto

    internal val DailyMottos = mutableListOf<Losungen?>()

    init {
        loadDailyMottos()
    }

    private fun loadDailyMottos(){
        val parser = context?.resources?.getXml(R.xml.losungen)
        if (parser != null) {
            var eventType = parser.eventType
            var losungen:Losungen?=null
            var text:String?=null
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType){
                    XmlPullParser.START_TAG->if (tagName.equals("Losungen")) {
                        losungen =Losungen()
                    }
                    XmlPullParser.TEXT ->text = parser.text
                    XmlPullParser.END_TAG->{
                        when(tagName){
                            "Losungen"->DailyMottos.add(losungen)
                            "Datum"->losungen?.Datum=text
                            "Wtag"->losungen?.Wtag=text
                            "Sonntag"->losungen?.Sonntag=text
                            "Losungstext"->losungen?.Losungstext=text
                            "Losungsvers"->losungen?.Losungsvers=text
                            "Lehrtext"->losungen?.Lehrtext=text
                            "Lehrtextvers"->losungen?.Lehrtextvers=text
                        }
                    }
                }
                eventType = parser.next();
            }
        }
        parser?.close()
    }
}