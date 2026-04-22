package com.marcusrunge.stjohannisuelzen

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.utils.QuotesRemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


/**
 * An [AppWidgetProvider] for displaying the daily motto (Tageslosung).
 *
 * This widget displays a quote and an inspiration text that changes daily. It supports
 * different presentations for Android versions before and after Android S (API 31).
 *
 * For API levels below S, it uses a [RemoteViewsService] to populate a `ListView`.
 * For API levels S and above, it uses the modern `RemoteCollectionItems.Builder` API to display
 * a single, non-scrollable item.
 */
@AndroidEntryPoint
class DailyMottoWidget : AppWidgetProvider() {

    /** Injected dependency for fetching daily motto data. */
    @Inject
    lateinit var dailyMotto: DailyMotto

    /**
     * A temporary in-memory cache for the most recently fetched daily motto.
     * This is populated in [loadDailyMotto] and used in [updateAppWidget].
     * Note: This is a simple caching approach. For more robust data handling across process
     * deaths, a database or file-based cache would be more appropriate.
     */
    private val dailyMottos =
        mutableListOf<com.marcusrunge.stjohannisuelzen.models.DailyMotto>()

    /**
     * Called when the widget is first created.
     *
     * @param context The context in which the receiver is running.
     */
    override fun onEnabled(context: Context) {
        // This is a good place to perform any one-time setup, such as scheduling a
        // recurring update alarm if more frequent updates than the manifest provides are needed.
    }

    /**
     * Called when the last widget is removed.
     *
     * @param context The context in which the receiver is running.
     */
    override fun onDisabled(context: Context) {
        // This is a good place to clean up any resources, like canceling alarms,
        // that were set up in onEnabled().
    }

    /**
     * Called to update the widget at intervals defined by `updatePeriodMillis` in the
     * widget provider info XML. Also called when the user adds the widget.
     *
     * This method performs the data loading and UI updates on a background thread
     * using `goAsync()` to prevent blocking the main thread.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The [AppWidgetManager] instance for this widget.
     * @param appWidgetIds An array of widget IDs to update.
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // Use goAsync() to allow for background work beyond the lifespan of onReceive().
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Load the latest daily motto data.
                loadDailyMotto()

                // Update each widget instance with the new data.
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }

                // For API < S, we must manually notify that the collection view data has changed.
                // For API >= S, this is handled automatically by RemoteCollectionItems.Builder.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    appWidgetManager.notifyAppWidgetViewDataChanged(
                        appWidgetIds,
                        R.id.listview_quotes
                    )
                }
            } finally {
                // Always call finish() on the pending result to release the wake lock.
                pendingResult.finish()
            }
        }
    }

    /**
     * Fetches the daily motto data from the [dailyMotto] source and populates the
     * [dailyMottos] list. This is a suspend function, designed to be called from a coroutine.
     * It fetches the quote and inspiration concurrently for better performance.
     */
    private suspend fun loadDailyMotto() {
        coroutineScope {
            val time = Calendar.getInstance().time
            // Launch both data fetches in parallel.
            val quoteDeferred = async { dailyMotto.quote.getAsync(time) }
            val inspirationDeferred = async { dailyMotto.inspiration.getAsync(time) }

            // Await the results.
            val quoteData = quoteDeferred.await()
            val inspirationData = inspirationDeferred.await()

            // Update the cached data.
            dailyMottos.clear()
            dailyMottos.add(
                com.marcusrunge.stjohannisuelzen.models.DailyMotto(
                    quoteData?.first,
                    quoteData?.second,
                    inspirationData?.first,
                    inspirationData?.second
                )
            )
        }
    }

    /**
     * Constructs the [RemoteViews] for a single widget instance and updates it.
     *
     * This function handles the different UI implementations required for Android versions
     * before and after Android S (API 31).
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The [AppWidgetManager] to use for the update.
     * @param appWidgetId The ID of the widget instance to update.
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Create the base RemoteViews for the widget layout.
        val widgetRemoteViews = RemoteViews(context.packageName, R.layout.daily_motto_widget)

        // Create a PendingIntent to launch MainActivity when the widget is clicked.
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        widgetRemoteViews.setOnClickPendingIntent(R.id.appwidget_root, pendingIntent)

        // For Android S (API 31) and above, we use RemoteCollectionItems.
        // This is the modern approach, better for "glanceable" non-scrolling content.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (dailyMottos.isNotEmpty()) {
                val dailyMottoRemoteView =
                    RemoteViews(context.packageName, R.layout.viewholder_dailymotto)
                val motto = dailyMottos[0] // Show the first (and only) motto.
                dailyMottoRemoteView.apply {
                    setTextViewText(R.id.dailymotto_quotecontent, motto.quoteContent)
                    setTextViewText(R.id.dailymotto_quoteverse, motto.quoteVerse)
                    setTextViewText(R.id.dailymotto_inspirationcontent, motto.inspirationContent)
                    setTextViewText(R.id.dailymotto_inspirationverse, motto.inspirationVerse)
                }

                val items = RemoteViews.RemoteCollectionItems.Builder()
                    .addItem(0, dailyMottoRemoteView)
                    .build()
                widgetRemoteViews.setRemoteAdapter(R.id.listview_quotes, items)
            }
        } else {
            // For older versions, we use a RemoteViewsService to populate a ListView,
            // which allows for a scrollable list of items within the widget.
            val quotesRemoteViewsServiceIntent =
                Intent(context, QuotesRemoteViewsService::class.java)
            widgetRemoteViews.setRemoteAdapter(
                R.id.listview_quotes,
                quotesRemoteViewsServiceIntent
            )
        }

        // Instruct the widget manager to update the widget.
        appWidgetManager.updateAppWidget(appWidgetId, widgetRemoteViews)
    }
}
