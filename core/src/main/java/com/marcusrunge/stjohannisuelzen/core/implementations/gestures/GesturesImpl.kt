package com.marcusrunge.stjohannisuelzen.core.implementations.gestures

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.implementations.swipe.SwipeImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures

internal class GesturesImpl(coreBase: CoreBase) : GesturesBase(coreBase) {
    internal companion object {
        var back: Gestures? = null
        fun create(coreBase: CoreBase): Gestures = when {
            back != null -> back!!
            else -> {
                back = GesturesImpl(coreBase)
                back!!
            }
        }
    }

    init {
        _swipe = SwipeImpl.create(this)
    }
}