package com.marcusrunge.stjohannisuelzen.webcontroller.bases

import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Sources
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal abstract class WebControllerBase : WebController {

    protected lateinit var _sources: Sources

    override val sources: Sources
        get() = _sources
}