package com.marcusrunge.stjohannisuelzen.apiconnect.interfaces

import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchList

interface YouTube {
    suspend fun getYoutubeSearchList(
        key: String?,
        channelId: String?,
        onSuccess: ((youtubeSearchList: YoutubeSearchList)->Unit)?,
        onError:((message:String?)->Unit)?
    )
}