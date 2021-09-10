package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core

internal abstract class CoreBase() : Core {
    protected lateinit var _back: Back

    override val back: Back
        get() = _back
}