package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Notifications

internal class NewsFeedImpl(context: Context?): NewsFeedBase(context) {
    init {
        _content = ContentImpl.create(this)
        _notifications = NotificationsImpl.create(this)
    }
    override val content: Content
        get() = _content
    override val notifications: Notifications
        get() = _notifications
}