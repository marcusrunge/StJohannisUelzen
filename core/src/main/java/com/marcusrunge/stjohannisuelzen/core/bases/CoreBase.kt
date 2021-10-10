package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

internal abstract class CoreBase : Core {
    protected lateinit var _back: Back
    protected lateinit var _webNavigation: WebNavigation

    override val back: Back
        get() = _back
    override val webNavigation: WebNavigation
        get() = _webNavigation
}