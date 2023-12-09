package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Notifications

internal class NotificationsImpl(newsFeedBase: NewsFeedBase) :Notifications {
    companion object {
        private var notifications: Notifications? = null
        fun create(newsFeedBase: NewsFeedBase): Notifications = when {
            notifications != null -> notifications!!
            else -> {
                notifications = NotificationsImpl(newsFeedBase)
                notifications!!
            }
        }
    }
    override fun create() {
        TODO("Not yet implemented")
    }

    override fun schedule() {
        TODO("Not yet implemented")
    }
}