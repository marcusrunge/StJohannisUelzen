package com.marcusrunge.stjohannisuelzen.core.implementation.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementation.back.BackImpl
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal class CoreImpl(webController:WebController) : CoreBase() {
    init {
        _back = BackImpl.create()
        _webController = webController
    }
}