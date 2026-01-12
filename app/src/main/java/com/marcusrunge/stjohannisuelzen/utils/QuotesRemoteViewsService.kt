package com.marcusrunge.stjohannisuelzen.utils

import android.content.Intent
import android.widget.RemoteViewsService
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A [RemoteViewsService] that provides the [QuotesRemoteViewsFactory] to a remote collection view.
 *
 * This service is responsible for creating and returning a [RemoteViewsFactory] that populates
 * a widget with daily motto data. It uses Hilt for dependency injection to get an instance
 * of [DailyMotto].
 */
@AndroidEntryPoint
class QuotesRemoteViewsService : RemoteViewsService() {

    /**
     * Injected instance of [DailyMotto] used to fetch the daily motto data.
     * Hilt provides this dependency.
     */
    @Inject
    lateinit var dailyMotto: DailyMotto

    /**
     * Creates and returns a [QuotesRemoteViewsFactory].
     *
     * This method is called by the system to get the factory for the remote collection view.
     * The factory is provided with the application context and the [dailyMotto] instance
     * so it can fetch the necessary data and create the views.
     *
     * @param intent The [Intent] that was used to bind to this service.
     * @return An instance of [QuotesRemoteViewsFactory].
     */
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return QuotesRemoteViewsFactory(applicationContext, dailyMotto)
    }
}
