package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.marcusrunge.stjohannisuelzen.apiconnect.bases.ApiConnectBase
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube
import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchList

internal class YouTubeImpl(private val apiConnectBase: ApiConnectBase) : YouTube {
    companion object {
        private var youTube: YouTube? = null
        fun create(apiConnectBase: ApiConnectBase): YouTube = when {
            youTube != null -> youTube!!
            else -> {
                youTube = YouTubeImpl(apiConnectBase)
                youTube!!
            }
        }
    }

    override suspend fun getYoutubeSearchList(
        key: String?,
        channelId: String?,
        onSuccess: ((youtubeSearchList: YoutubeSearchList) -> Unit)?,
        onError: ((message: String?) -> Unit)?
    ) {
        val requestQueue = Volley.newRequestQueue(apiConnectBase.context)
        val url =
            "https://www.googleapis.com/youtube/v3/search?key=$key&channelId=$channelId&part=snippet,id&order=date&maxResults=500"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val youtubeSearchList = gson.fromJson(response, YoutubeSearchList::class.java)
                youtubeSearchList.items
                onSuccess?.invoke(youtubeSearchList)
            },
            { error ->
                onError?.invoke("StatusCode:${error.networkResponse.statusCode}")
            })
        requestQueue.add(stringRequest)
    }
}