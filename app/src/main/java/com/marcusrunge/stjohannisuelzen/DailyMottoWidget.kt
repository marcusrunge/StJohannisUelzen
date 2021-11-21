package com.marcusrunge.stjohannisuelzen

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
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
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listview_quotes)
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
        val mainActivity = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val quotesRemoteViewsService = Intent(context, QuotesRemoteViewsService::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, mainActivity, FLAG_IMMUTABLE)
        views.apply {
            setRemoteAdapter(R.id.listview_quotes, quotesRemoteViewsService)
            setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}