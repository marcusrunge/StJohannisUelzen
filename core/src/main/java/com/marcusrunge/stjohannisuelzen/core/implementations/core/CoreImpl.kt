package com.marcusrunge.stjohannisuelzen.core.implementations.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementations.back.BackImpl

internal class CoreImpl() : CoreBase() {
    init {
        _back = BackImpl.create(this)
    }
}