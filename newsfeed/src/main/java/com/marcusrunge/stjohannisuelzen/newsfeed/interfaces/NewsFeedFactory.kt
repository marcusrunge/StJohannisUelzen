package com.marcusrunge.stjohannisuelzen.newsfeed.interfaces

import android.content.Context

interface NewsFeedFactory {
    fun create(context: Context?): NewsFeed
}