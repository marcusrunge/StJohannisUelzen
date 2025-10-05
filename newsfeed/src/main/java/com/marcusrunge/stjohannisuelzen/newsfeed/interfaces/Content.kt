package com.marcusrunge.stjohannisuelzen.newsfeed.interfaces

interface Content {
    suspend fun parseAsync(url: String): Triple<String, String, String>?
}