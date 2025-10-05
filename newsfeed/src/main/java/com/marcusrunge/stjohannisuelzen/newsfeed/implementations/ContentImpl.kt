package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content
import org.jsoup.Jsoup
import java.io.IOException

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

    override suspend fun parseAsync(url: String): Triple<String, String, String>? {
        return try {
            val document = Jsoup.connect(url).get()
            val latestPost = document.selectFirst(
                "turbo-frame[id~=^(post_)[a-zA-Z0-9]{8}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{12}$]"
            ) ?: return null
            val title = latestPost.selectFirst(".body a[href]")?.text()
            val text = latestPost.selectFirst(".post-content.clearfix div:not([class])")?.text()
            Triple(latestPost.id(), title.orEmpty(), text.orEmpty())
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}