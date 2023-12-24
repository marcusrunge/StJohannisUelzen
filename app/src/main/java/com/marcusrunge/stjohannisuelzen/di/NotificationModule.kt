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

object NotificationModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object ApiConnectModule {
        @Provides
        @Singleton
        fun provideNotification(
            @ApplicationContext context: Context?,
            dailyMotto: DailyMotto?,
            newsFeed: NewsFeed?
        ): Notification =
            NotificationFactoryImpl.create(context, dailyMotto, newsFeed, MainActivity::class.java)
    }
}