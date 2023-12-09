package com.marcusrunge.stjohannisuelzen.newsfeed.interfaces

import android.net.Uri

interface Content {
    suspend fun parseAsync(url:Uri)
}