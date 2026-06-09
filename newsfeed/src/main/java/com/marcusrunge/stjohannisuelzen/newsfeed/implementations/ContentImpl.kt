package com.marcusrunge.stjohannisuelzen.newsfeed.implementations

import com.marcusrunge.stjohannisuelzen.newsfeed.bases.NewsFeedBase
import com.marcusrunge.stjohannisuelzen.newsfeed.interfaces.Content
import com.marcusrunge.stjohannisuelzen.newsfeed.models.BlogNotificationEntry
import org.jsoup.Jsoup

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

    override suspend fun getLatestBlogEntry(url: String): BlogNotificationEntry? {
        // 1. Connect and fetch document safely
        val document = runCatching { Jsoup.connect(url).get() }.getOrNull() ?: return null

        // 2. Locate all post frames using a highly performant CSS prefix selector
        val postFrames = document.select("turbo-frame[id^=post_]")

        // 3. Find the first valid blog entry (skipping elements that don't match our criteria)
        for (postFrame in postFrames) {
            val fullId = postFrame.id()
            val guid = fullId.removePrefix("post_")

            // Robust UUID validation performed directly in Kotlin
            val uuidRegex =
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$".toRegex()
            if (!uuidRegex.matches(guid)) continue // Skip if it doesn't match a true GUID layout

            // 4. Extract title with explicit heading tags to avoid catching author profile links
            val title = postFrame.selectFirst(".body h3 a, .post-header h3 a")?.text()
                ?: postFrame.selectFirst("h3")?.text() // Fallback if title is unlinked
                ?: continue

            // 5. Extract text excerpt robustly from the parent container
            val contentContainer = postFrame.selectFirst(".post-content") ?: continue

            // Optional: If there are metadata elements inside .post-content you want to exclude
            // contentContainer.select(".social-bar, .sharing-dropdown").remove()

            // Clean up the string and limit the characters for an Android Notification
            val textExcerpt = contentContainer.text().trim()
            val finalExcerpt =
                if (textExcerpt.length > 120) "${textExcerpt.take(117)}..." else textExcerpt

            return BlogNotificationEntry(
                id = fullId,
                title = title,
                excerpt = finalExcerpt
            )
        }

        return null
    }
}