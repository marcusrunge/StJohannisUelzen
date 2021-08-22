package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal abstract class CoreBase() : Core {
    protected lateinit var _back: Back
    protected lateinit var _webController: WebController

    override val back: Back
        get() = _back

    override val webController: WebController
        get() = _webController
}