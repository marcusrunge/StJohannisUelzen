package com.marcusrunge.stjohannisuelzen.apiconnect.bases

import android.content.Context
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ChurchServices
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube

internal abstract class ApiConnectBase(internal val context: Context?) : ApiConnect {
    protected lateinit var _youTube: YouTube
    protected lateinit var _churchServices: ChurchServices
}