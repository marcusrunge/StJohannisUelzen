package com.marcusrunge.stjohannisuelzen.newsfeed.bases

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.NewsFeed

internal abstract class NewsFeedBase(internal val context: Context?) : NewsFeed {
    protected lateinit var _content: Content
}