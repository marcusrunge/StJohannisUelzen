package com.marcusrunge.stjohannisuelzen.apiconnect.interfaces

import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchList

interface YouTube {
    /**
     * Gets the YouTube search list.
     * @param key the API key
     * @param channelId the YouTube channel ID.
     * @param onError the error callback.
     * @param onSuccess the success callback containing the list.
     */
    suspend fun getYoutubeSearchList(
        key: String?,
        channelId: String?,
        onSuccess: ((youtubeSearchList: YoutubeSearchList) -> Unit)?,
        onError: ((message: String?) -> Unit)?
    )
}