package com.marcusrunge.stjohannisuelzen

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.marcusrunge.stjohannisuelzen.utils.QuotesRemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto as DailyMottoInterface
import com.marcusrunge.stjohannisuelzen.models.DailyMotto as DailyMottoModel

/**
 * A widget that displays the daily motto (quote and inspiration).
 * Uses Hilt for dependency injection to fetch data.
 */
@AndroidEntryPoint
class DailyMottoWidget : AppWidgetProvider() {

    @Inject
    lateinit var dailyMotto: DailyMottoInterface

    /**
     * Cache for the daily motto items to be displayed in the widget.
     */
    private val dailyMottos = mutableListOf<DailyMottoModel>()

    /**
     * Called to update the AppWidget in response to a broadcast.
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // Use goAsync to allow long-running operations in the broadcast receiver.
        val pendingResult = goAsync()

        // Launch a coroutine on the IO dispatcher to fetch data and update the widget.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch the latest motto data.
                loadDailyMotto()

                // Update each instance of the widget.
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }

                // For older Android versions (pre-S), we need to manually notify the collection view
                // because it uses a RemoteViewsService adapter.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    notifyLegacyCollectionDataChanged(
                        appWidgetManager = appWidgetManager,
                        appWidgetIds = appWidgetIds
                    )
                }
            } finally {
                // Must call finish() to signal completion of the async operation.
                pendingResult.finish()
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Called when the first instance of the widget is added.
    }

    override fun onDisabled(context: Context) {
        // Called when the last instance of the widget is removed.
    }

    /**
     * Loads the daily motto data for the current date.
     * Fetches quote and inspiration concurrently using coroutines.
     */
    private suspend fun loadDailyMotto() {
        coroutineScope {
            val time = Calendar.getInstance().time

            // Execute fetching tasks concurrently.
            val quoteDeferred = async { dailyMotto.quote.getAsync(time) }
            val inspirationDeferred = async { dailyMotto.inspiration.getAsync(time) }

            val quoteData = quoteDeferred.await()
            val inspirationData = inspirationDeferred.await()

            dailyMottos.clear()
            dailyMottos.add(
                DailyMottoModel(
                    quoteData?.first,
                    quoteData?.second,
                    inspirationData?.first,
                    inspirationData?.second
                )
            )
        }
    }

    /**
     * Updates the UI of a single widget instance.
     * Sets up the click intent and selects the appropriate adapter based on API level.
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val widgetRemoteViews = RemoteViews(context.packageName, R.layout.daily_motto_widget)

        // Set up intent to open MainActivity when the widget background is clicked.
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        widgetRemoteViews.setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)

        // Android 12 (API 31) introduced a simpler way to set items for RemoteViews collections.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setModernRemoteCollectionItems(context, widgetRemoteViews)
        } else {
            setLegacyRemoteAdapter(context, widgetRemoteViews)
        }

        // Apply the updates to the widget.
        appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteViews)
    }

    /**
     * Sets the remote collection items using the modern API 31+ method (setRemoteAdapter with items).
     */
    @RequiresApi(Build.VERSION_CODES.S)
    private fun setModernRemoteCollectionItems(
        context: Context,
        widgetRemoteViews: RemoteViews
    ) {
        if (dailyMottos.isEmpty()) return

        val motto = dailyMottos.first()

        // Create a RemoteViews for the motto item from the viewholder layout.
        val dailyMottoRemoteView =
            RemoteViews(context.packageName, R.layout.viewholder_dailymotto).apply {
                setTextViewText(R.id.dailymotto_quotecontent, motto.quoteContent)
                setTextViewText(R.id.dailymotto_quoteverse, motto.quoteVerse)
                setTextViewText(R.id.dailymotto_inspirationcontent, motto.inspirationContent)
                setTextViewText(R.id.dailymotto_inspirationverse, motto.inspirationVerse)
            }

        // Build the collection containing the single motto item.
        val items = RemoteViews.RemoteCollectionItems.Builder()
            .addItem(0L, dailyMottoRemoteView)
            .setHasStableIds(true)
            .setViewTypeCount(1)
            .build()

        widgetRemoteViews.setRemoteAdapter(R.id.listview_quotes, items)
    }

    /**
     * Sets the remote adapter using the legacy RemoteViewsService (for API < 31).
     * This relies on a separate service to provide the views.
     */
    @Suppress("DEPRECATION")
    private fun setLegacyRemoteAdapter(
        context: Context,
        widgetRemoteViews: RemoteViews
    ) {
        val quotesRemoteViewsServiceIntent =
            Intent(context, QuotesRemoteViewsService::class.java)

        widgetRemoteViews.setRemoteAdapter(
            R.id.listview_quotes,
            quotesRemoteViewsServiceIntent
        )
    }

    /**
     * Notifies the legacy collection view that its data has changed, triggering a refresh.
     */
    @Suppress("DEPRECATION")
    private fun notifyLegacyCollectionDataChanged(
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetManager.notifyAppWidgetViewDataChanged(
            appWidgetIds,
            R.id.listview_quotes
        )
    }
}
