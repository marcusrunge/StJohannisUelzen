package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.ApiKeys
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

internal abstract class CoreBase : Core {
    protected lateinit var _apiKeys: ApiKeys
    protected lateinit var _back: Back
    protected lateinit var _webNavigation: WebNavigation
    protected lateinit var _gestures: Gestures

    override val apiKeys: ApiKeys
        get() = _apiKeys
    override val back: Back
        get() = _back
    override val webNavigation: WebNavigation
        get() = _webNavigation
    override val gestures: Gestures
        get() = _gestures
}