package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.bases.WebControllerBase
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal class WebControllerImpl : WebControllerBase() {

    init {
        _sources = SourcesImpl.create(this)
        _control = ControlImpl.create(this)
    }
}