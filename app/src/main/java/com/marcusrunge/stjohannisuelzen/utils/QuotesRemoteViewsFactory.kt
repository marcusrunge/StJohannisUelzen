package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.models.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


class QuotesRemoteViewsFactory(
    private val context: Context,
    private val dailyMotto: DailyMotto
):
    RemoteViewsService.RemoteViewsFactory {
    private val quotes = mutableListOf<Quote>()

    override fun onCreate() {
        loadQuotes()
    }

    override fun onDataSetChanged() {
        loadQuotes()
    }

    override fun onDestroy() {
        quotes.clear()
    }

    override fun getCount(): Int = quotes.size

    override fun getViewAt(position: Int): RemoteViews {
        val quote = quotes[position]
        val remoteViews = RemoteViews(context.packageName, R.layout.viewholder_quote)
        remoteViews.apply {
            setTextViewText(R.id.quote_content, quote.content)
            setTextViewText(R.id.quote_verse, quote.verse)
            if(position+1 == quotes.size){
                setViewVisibility(R.id.quote_divider, View.GONE )
            }
        }
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    private fun loadQuotes(){
        runBlocking(Dispatchers.IO){
            val time = Calendar.getInstance().time
            val first = dailyMotto.quote.getAsync(time)
            val second = dailyMotto.inspiration.getAsync(Calendar.getInstance().time)
            quotes.clear()
            quotes.add(Quote(first?.first, first?.second))
            quotes.add(Quote(second?.first, second?.second))
        }
    }
}