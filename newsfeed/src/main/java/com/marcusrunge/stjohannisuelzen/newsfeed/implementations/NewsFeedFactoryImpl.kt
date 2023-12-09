package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeedFactory

class NewsFeedFactoryImpl {
    companion object : NewsFeedFactory {
        override fun create(
            context: Context?
        ): NewsFeed = NewsFeedImpl(context)
    }
}