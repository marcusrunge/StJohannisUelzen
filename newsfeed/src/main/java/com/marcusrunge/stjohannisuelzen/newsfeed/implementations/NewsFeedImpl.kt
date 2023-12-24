package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content

internal class NewsFeedImpl(context: Context?) : NewsFeedBase(context) {
    init {
        _content = ContentImpl.create(this)
    }

    override val content: Content
        get() = _content
}