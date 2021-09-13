package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube

internal class YouTubeImpl : YouTube {
    companion object {
        private var youTube: YouTube? = null
        fun create(): YouTube = when {
            youTube != null -> youTube!!
            else -> {
                youTube = YouTubeImpl()
                youTube!!
            }
        }
    }
}