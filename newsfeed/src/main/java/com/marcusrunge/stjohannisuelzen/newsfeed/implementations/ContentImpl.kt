package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal class ContentImpl(newsFeedBase: NewsFeedBase?) : Content {
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

    override suspend fun parseAsync(url: String): Document {
        return Jsoup.connect(url).get();
    }
}