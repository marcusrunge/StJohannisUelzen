package com.marcusrunge.stjohannisuelzen.webcontroller.bases

import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Control
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal abstract class WebControllerBase : WebController {

    protected lateinit var _control: Control

    override val control: Control
        get() = _control
}