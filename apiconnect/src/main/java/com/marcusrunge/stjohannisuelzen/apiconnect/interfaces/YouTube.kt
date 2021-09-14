package com.marcusrunge.stjohannisuelzen.apiconnect.interfaces

import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchListResponse

interface YouTube {
    suspend fun getYoutubeSearchList(
        key: String?,
        channelId: String?
    ): YoutubeSearchListResponse?
}