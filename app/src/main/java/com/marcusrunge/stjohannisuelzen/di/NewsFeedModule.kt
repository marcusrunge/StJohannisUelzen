package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.implementations.NewsFeedFactoryImpl
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module for providing the [NewsFeed] dependency.
 *
 * This module is installed in the [SingletonComponent], meaning that the provided [NewsFeed]
 * instance will be a singleton and live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object NewsFeedModule {

    /**
     * Provides a singleton instance of [NewsFeed].
     *
     * This method uses the [NewsFeedFactoryImpl] to create the instance.
     * It requires the application [Context], which is provided by Hilt.
     *
     * @param context The application context, injected by Hilt.
     * @return A singleton [NewsFeed] instance.
     */
    @Provides
    @Singleton
    fun provideNewsFeed(@ApplicationContext context: Context): NewsFeed =
        NewsFeedFactoryImpl.create(context)
}
