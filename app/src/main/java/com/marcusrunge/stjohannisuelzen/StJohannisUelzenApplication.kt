package com.marcusrunge.stjohannisuelzen

import android.app.Application
import androidx.work.Configuration
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class StJohannisUelzenApplication : Application(),  Configuration.Provider{
    @Inject
    lateinit var notification: Notification
    override fun getWorkManagerConfiguration(): Configuration {
        return notification.schedule.createWorkManagerConfiguration()
    }
}