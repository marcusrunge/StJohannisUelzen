package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube
import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchListResponse

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

    override suspend fun getYoutubeSearchList(
        key: String?,
        channelId: String?
    ): Result<YoutubeSearchListResponse> {
        try {
            TODO("Not yet implemented")
        } catch (e: Exception) {
            Result.failure<Exception>(e)
        }
        TODO("Not yet implemented")
    }
}