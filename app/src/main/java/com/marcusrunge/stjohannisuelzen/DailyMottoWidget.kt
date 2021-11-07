package com.marcusrunge.stjohannisuelzen

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE

import android.content.Intent




/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
class DailyMottoWidget : AppWidgetProvider() {
    @Inject
    lateinit var dailyMotto: DailyMotto

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        CoroutineScope(Dispatchers.IO).launch {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private suspend fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val quote = dailyMotto.quote.getAsync(Calendar.getInstance().time)
        val inspiration = dailyMotto.inspiration.getAsync(Calendar.getInstance().time)
        val views = RemoteViews(context.packageName, R.layout.daily_motto_widget)
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)
        views.apply {
            setTextViewText(R.id.appwidget_quote, quote?.first)
            setTextViewText(R.id.appwidget_quoteverse, quote?.second)
            setTextViewText(R.id.appwidget_inspiration, inspiration?.first)
            setTextViewText(R.id.appwidget_inspirationverse, inspiration?.second)
            setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)
        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}