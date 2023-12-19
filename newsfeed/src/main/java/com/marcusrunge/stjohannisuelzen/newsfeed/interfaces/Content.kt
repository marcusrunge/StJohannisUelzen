package com.marcusrunge.stjohannisuelzen.newsfeed.interfaces

import org.jsoup.nodes.Document

interface Content {
    suspend fun parseAsync(url: String):Pair<String,String>
}