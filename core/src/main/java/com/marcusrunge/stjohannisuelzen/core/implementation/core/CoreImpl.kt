package com.marcusrunge.stjohannisuelzen.core.implementation.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementation.back.BackImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal class CoreImpl(webController:WebController) : CoreBase(), OnBackSubscriber {
    init {
        _back = BackImpl.create(this)
        _webController = webController
        _back.subscriber.add(this)
    }

    override fun onBack() {
        _webController.control.goBack()
    }
}