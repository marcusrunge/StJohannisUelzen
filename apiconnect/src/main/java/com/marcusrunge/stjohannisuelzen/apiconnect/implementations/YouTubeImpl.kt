package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.marcusrunge.stjohannisuelzen.apiconnect.bases.ApiConnectBase
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube
import com.marcusrunge.stjohannisuelzen.apiconnect.models.YoutubeSearchListResponse

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
        channelId: String?
    ): YoutubeSearchListResponse? {
        try {
            val requestQueue = Volley.newRequestQueue(apiConnectBase.context)
            val url ="https://www.googleapis.com/youtube/v3/search?key=$key&channelId=$channelId&part=snippet,id&order=date"
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    // TODO: Do something with the response
                },
                { error ->
                    // TODO: Handle error
                 })
            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            // TODO: Handle exception
        }
        return null
    }
}