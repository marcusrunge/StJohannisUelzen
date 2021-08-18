package com.marcusrunge.stjohannisuelzen.webcontroller.bases

import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Control
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Sources
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal abstract class WebControllerBase : WebController {

    protected lateinit var _sources: Sources
    protected lateinit var _control: Control

    override val sources: Sources
        get() = _sources

    override val control: Control
        get() = _control
}