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
import com.marcusrunge.stjohannisuelzen.models.Quote
import com.marcusrunge.stjohannisuelzen.utils.QuotesRemoteViewsService


/**
 * Implementation of App Widget functionality.
 */

class DailyMottoWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.daily_motto_widget)
        val mainActivity = Intent(context, MainActivity::class.java)
        val quotesRemoteViewsService = Intent(context, QuotesRemoteViewsService::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, mainActivity, FLAG_IMMUTABLE)
        views.apply {
            setRemoteAdapter(R.id.listview_quotes, quotesRemoteViewsService)
            setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}