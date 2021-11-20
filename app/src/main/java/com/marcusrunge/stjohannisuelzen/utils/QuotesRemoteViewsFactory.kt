package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*


class QuotesRemoteViewsFactory(
    private val context: Context,
    private val dailyMotto: DailyMotto
) :
    RemoteViewsService.RemoteViewsFactory {
    private val dailymottos = mutableListOf<com.marcusrunge.stjohannisuelzen.models.DailyMotto>()

    override fun onCreate() {
        loadDailyMotto()
    }

    override fun onDataSetChanged() {
        loadDailyMotto()
    }

    override fun onDestroy() {
        dailymottos.clear()
    }

    override fun getCount(): Int = dailymottos.size

    override fun getViewAt(position: Int): RemoteViews {
        val quote = dailymottos[position]
        val remoteViews = RemoteViews(context.packageName, R.layout.viewholder_dailymotto)
        remoteViews.apply {
            setTextViewText(R.id.dailymotto_quotecontent, quote.quoteContent)
            setTextViewText(R.id.dailymotto_quoteverse, quote.quoteVerse)
            setTextViewText(R.id.dailymotto_inspirationcontent, quote.inspirationContent)
            setTextViewText(R.id.dailymotto_inspirationverse, quote.inspirationVerse)
        }
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    private fun loadDailyMotto() {
        runBlocking(Dispatchers.IO) {
            val time = Calendar.getInstance().time
            val first = dailyMotto.quote.getAsync(time)
            val second = dailyMotto.inspiration.getAsync(Calendar.getInstance().time)
            dailymottos.clear()
            dailymottos.add(
                com.marcusrunge.stjohannisuelzen.models.DailyMotto(
                    first?.first,
                    first?.second,
                    second?.first,
                    second?.second
                )
            )
        }
    }
}