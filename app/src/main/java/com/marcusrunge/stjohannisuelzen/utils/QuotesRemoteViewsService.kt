package com.marcusrunge.stjohannisuelzen.utils

import android.content.Intent
import android.widget.RemoteViewsService
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuotesRemoteViewsService : RemoteViewsService() {
    @Inject
    lateinit var dailyMotto: DailyMotto
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return QuotesRemoteViewsFactory(applicationContext, dailyMotto)
    }
}