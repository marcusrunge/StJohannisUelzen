package com.marcusrunge.stjohannisuelzen

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.utils.QuotesRemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import javax.inject.Inject


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
        appWidgetIds: IntArray,
    ) {
        loadDailyMotto()
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listview_quotes)
    }

    private val dailymottos = mutableListOf<com.marcusrunge.stjohannisuelzen.models.DailyMotto>()
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
        val widgetRemoteViews = RemoteViews(context.packageName, R.layout.daily_motto_widget)
        val mainActivity = Intent(context, MainActivity::class.java)/*.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val quotesRemoteViewsService = Intent(context, QuotesRemoteViewsService::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivity,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            widgetRemoteViews.apply {
                setRemoteAdapter(R.id.listview_quotes, quotesRemoteViewsService)
                setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)
            }
        } else {
            val dailyMottoRemoteView = RemoteViews(context.packageName, R.layout.viewholder_dailymotto)
            val quote = dailymottos[0]
            dailyMottoRemoteView.apply {
                setTextViewText(R.id.dailymotto_quotecontent, quote.quoteContent)
                setTextViewText(R.id.dailymotto_quoteverse, quote.quoteVerse)
                setTextViewText(R.id.dailymotto_inspirationcontent, quote.inspirationContent)
                setTextViewText(R.id.dailymotto_inspirationverse, quote.inspirationVerse)
            }

            widgetRemoteViews.apply {
                setRemoteAdapter(
                    R.id.listview_quotes,
                    RemoteViews.RemoteCollectionItems.Builder().addItem(0, dailyMottoRemoteView).build()
                )
                setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteViews)
    }

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