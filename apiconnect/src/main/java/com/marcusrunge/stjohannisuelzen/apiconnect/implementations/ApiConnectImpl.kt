package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.marcusrunge.stjohannisuelzen.apiconnect.bases.ApiConnectBase
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.Counseling
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube

internal class ApiConnectImpl : ApiConnectBase() {
    init {
        _youTube = YouTubeImpl.create()
        _counseling = CounselingImpl.create()
    }

    override val youTube: YouTube
        get() = _youTube
    override val counseling: Counseling
        get() = _counseling
}