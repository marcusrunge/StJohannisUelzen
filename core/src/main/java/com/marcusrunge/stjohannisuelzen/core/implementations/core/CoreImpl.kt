package com.marcusrunge.stjohannisuelzen.core.implementations.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementations.back.BackImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.gestures.GesturesImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.webnavigation.WebNavigationImpl

internal class CoreImpl : CoreBase() {
    init {
        _back = BackImpl.create(this)
        _webNavigation = WebNavigationImpl.create()
        _gestures = GesturesImpl.create(this)
    }
}