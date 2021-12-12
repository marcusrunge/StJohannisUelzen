package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.Swipe

internal abstract class GesturesBase(val coreBase: CoreBase) : Gestures {
    protected lateinit var _swipe: Swipe
    override val swipe: Swipe
        get() = _swipe
}