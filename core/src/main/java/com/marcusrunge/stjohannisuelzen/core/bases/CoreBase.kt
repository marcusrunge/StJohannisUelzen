package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

internal abstract class CoreBase : Core {
    protected lateinit var _back: Back
    protected lateinit var _webNavigation: WebNavigation
    protected lateinit var _gestures: Gestures

    override val back: Back
        get() = _back
    override val webNavigation: WebNavigation
        get() = _webNavigation
    override val gestures: Gestures
        get() = _gestures
}