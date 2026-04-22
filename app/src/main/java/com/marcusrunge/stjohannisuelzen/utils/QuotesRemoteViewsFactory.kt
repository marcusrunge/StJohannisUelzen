package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.models.DailyMotto as DailyMottoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.Calendar

/**
 * A [RemoteViewsService.RemoteViewsFactory] for populating a remote collection view with daily motto quotes.
 *
 * This factory is responsible for fetching the daily motto data and creating the corresponding [RemoteViews]
 * to be displayed in a widget or other remote view.
 *
 * @param context The application context.
 * @param dailyMotto An instance of the [DailyMotto] interface used to fetch quote and inspiration data.
 */
class QuotesRemoteViewsFactory(
    private val context: Context,
    private val dailyMotto: DailyMotto
) : RemoteViewsService.RemoteViewsFactory {

    private val dailyMottoItems = mutableListOf<DailyMottoModel>()

    /**
     * Called when the factory is created. Initializes the data set.
     */
    override fun onCreate() {
        loadDailyMotto()
    }

    /**
     * Called whenever the data set for the remote view needs to be updated.
     */
    override fun onDataSetChanged() {
        loadDailyMotto()
    }

    /**
     * Called when the factory is destroyed. Cleans up any resources.
     */
    override fun onDestroy() {
        dailyMottoItems.clear()
    }

    /**
     * Returns the number of items in the data set.
     */
    override fun getCount(): Int = dailyMottoItems.size

    /**
     * Creates and returns the [RemoteViews] for the item at the specified position.
     *
     * @param position The position of the item in the data set.
     * @return The [RemoteViews] for the item.
     */
    override fun getViewAt(position: Int): RemoteViews {
        val quote = dailyMottoItems[position]
        return RemoteViews(context.packageName, R.layout.viewholder_dailymotto).apply {
            setTextViewText(R.id.dailymotto_quotecontent, quote.quoteContent)
            setTextViewText(R.id.dailymotto_quoteverse, quote.quoteVerse)
            setTextViewText(R.id.dailymotto_inspirationcontent, quote.inspirationContent)
            setTextViewText(R.id.dailymotto_inspirationverse, quote.inspirationVerse)
        }
    }

    /**
     * Returns the [RemoteViews] to display while the data is loading.
     * Returning null will display the default loading view.
     */
    override fun getLoadingView(): RemoteViews? = null

    /**
     * Returns the number of distinct view types in the data set.
     */
    override fun getViewTypeCount(): Int = 1

    /**
     * Returns the item ID for the item at the specified position.
     */
    override fun getItemId(position: Int): Long = position.toLong()

    /**
     * Indicates whether the item IDs are stable across data set changes.
     */
    override fun hasStableIds(): Boolean = true

    /**
     * Loads the daily motto data. This method performs a blocking network call, as required
     * by the [RemoteViewsService.RemoteViewsFactory] lifecycle, which operates on a background thread.
     */
    private fun loadDailyMotto() {
        runBlocking(Dispatchers.IO) {
            val time = Calendar.getInstance().time
            val first = dailyMotto.quote.getAsync(time)
            val second = dailyMotto.inspiration.getAsync(time)
            dailyMottoItems.clear()
            dailyMottoItems.add(
                DailyMottoModel(
                    first?.first,
                    first?.second,
                    second?.first,
                    second?.second
                )
            )
        }
    }
}
