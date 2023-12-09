package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.implementations.NewsFeedFactoryImpl
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsFeedModule {
    @Provides
    fun provideNewsFeed(@ApplicationContext context: Context?): NewsFeed {
        return NewsFeedFactoryImpl.create(context)
    }
}