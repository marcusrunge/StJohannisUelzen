package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import android.net.Uri
import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content

internal class ContentImpl(newsFeedBase: NewsFeedBase?) :Content {
    companion object {
        private var content: Content? = null
        fun create(newsFeedBase: NewsFeedBase): Content = when {
            content != null -> content!!
            else -> {
                content = ContentImpl(newsFeedBase)
                content!!
            }
        }
    }
    override suspend fun parseAsync(url: Uri) {
        TODO("Not yet implemented")
    }
}