package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.apiconnect.bases.ApiConnectBase
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.Counseling
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.YouTube

internal class ApiConnectImpl(context: Context?) : ApiConnectBase(context) {
    init {
        _youTube = YouTubeImpl.create(this)
        _counseling = CounselingImpl.create()
    }

    override val youTube: YouTube
        get() = _youTube
    override val counseling: Counseling
        get() = _counseling
}