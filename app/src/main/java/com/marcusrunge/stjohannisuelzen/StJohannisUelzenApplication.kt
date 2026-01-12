package com.marcusrunge.stjohannisuelzen

import android.app.Application
import androidx.work.Configuration
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * The main application class for the St. Johannis Uelzen app.
 *
 * This class is annotated with [HiltAndroidApp] to enable Hilt for dependency injection
 * throughout the application. It also implements [Configuration.Provider] to provide a
 * custom configuration for WorkManager.
 */
@HiltAndroidApp
class StJohannisUelzenApplication : Application(), Configuration.Provider {

    /**
     * Injected instance of the [Notification] interface.
     * This provides access to notification-related functionalities, such as scheduling background work.
     */
    @Inject
    lateinit var notification: Notification

    /**
     * Provides the custom [WorkManager] configuration.
     *
     * This implementation retrieves the configuration from the injected [notification] service,
     * allowing for custom initialization of WorkManager, for example, to use Hilt for
     * dependency injection in Workers.
     *
     * @return The custom [Configuration] for WorkManager.
     */
    override val workManagerConfiguration: Configuration
        get() = notification.schedule.createWorkManagerConfiguration()
}
