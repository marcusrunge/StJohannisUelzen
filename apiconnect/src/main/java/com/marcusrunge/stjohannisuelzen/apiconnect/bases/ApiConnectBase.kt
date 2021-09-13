package com.marcusrunge.stjohannisuelzen.apiconnect.bases

import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.Counseling
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube

internal abstract class ApiConnectBase : ApiConnect {
    protected lateinit var _youTube: YouTube
    protected lateinit var _counseling: Counseling
}