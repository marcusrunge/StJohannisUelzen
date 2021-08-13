package com.marcusrunge.stjohannisuelzen.core.implementation.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementation.back.BackImpl

internal class CoreImpl : CoreBase() {
    init {
        _back = BackImpl.create()
    }
}