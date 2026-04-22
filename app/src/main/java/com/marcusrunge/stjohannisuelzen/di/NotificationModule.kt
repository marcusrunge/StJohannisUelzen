package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.MainActivity
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import com.marcusrunge.stjohannisuelzen.notification.implementations.NotificationFactoryImpl
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module for providing the [Notification] dependency.
 *
 * This module is installed in the [SingletonComponent], meaning that the provided [Notification]
 * instance will be a singleton and live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    /**
     * Provides a singleton instance of [Notification].
     *
     * This method uses the [NotificationFactoryImpl] to create the instance.
     * It requires the application [Context], [DailyMotto], and [NewsFeed] dependencies,
     * which are provided by Hilt. It also passes the [MainActivity] class for constructing
     * intents for the notifications.
     *
     * @param context The application context, injected by Hilt.
     * @param dailyMotto The DailyMotto dependency, injected by Hilt.
     * @param newsFeed The NewsFeed dependency, injected by Hilt.
     * @return A singleton [Notification] instance.
     */
    @Provides
    @Singleton
    fun provideNotification(
        @ApplicationContext context: Context,
        dailyMotto: DailyMotto,
        newsFeed: NewsFeed
    ): Notification = NotificationFactoryImpl.create(context, dailyMotto, newsFeed, MainActivity::class.java)
}
