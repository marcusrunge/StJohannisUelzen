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

    override suspend fun parseAsync(url: String): Pair<String,String> {
       val document =Jsoup.connect(url).get()
        val turboFramePosts = document.getElementsByAttributeValueMatching(
            "id",
            "^(post_)[a-zA-Z0-9]{8}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{4}[-.]?[a-zA-Z0-9]{12}\$"
        ).filter { x -> x.tagName() == "turbo-frame" }
        val latestPost = turboFramePosts.first()
        val body = latestPost.getElementsByTag("div").filter { x -> x.hasClass("body") }
        val title =
            body.first().getElementsByAttributeStarting("href").first()?.textNodes()?.first()
                ?.text()
        val content = latestPost.getElementsByTag("div")
            .filter { x -> x.hasClass("post-content clearfix") }
        val text =
            content.first().getElementsByTag("div").first()?.select("div:not([class])")?.first()
                ?.textNodes()?.first()?.text()
        return Pair(title!!, text!!)
    }
}