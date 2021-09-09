package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.bases.WebControllerBase

internal class WebControllerImpl : WebControllerBase() {

    init {
        _control = ControlImpl.create(this)
    }
}