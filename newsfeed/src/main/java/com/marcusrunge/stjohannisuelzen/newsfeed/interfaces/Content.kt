package com.marcusrunge.stjohannisuelzen.newsfeed.interfaces

import com.marcusrunge.stjohannisuelzen.newsfeed.models.BlogNotificationEntry

interface Content {
    suspend fun getLatestBlogEntry(url: String): BlogNotificationEntry?
}