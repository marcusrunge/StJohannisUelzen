package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.Scroll
import com.marcusrunge.stjohannisuelzen.core.interfaces.Swipe

internal abstract class GesturesBase(val coreBase: CoreBase) : Gestures {
    protected lateinit var _swipe: Swipe
    protected lateinit var _scroll: Scroll
    override val swipe: Swipe
        get() = _swipe
    override val scroll: Scroll
        get() = _scroll
}