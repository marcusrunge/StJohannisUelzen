package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.bases.WebControllerBase
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

internal class WebControllerImpl : WebControllerBase() {

    companion object {
        private var webController: WebController? = null
        fun create(): WebController = when {
            webController != null -> webController!!
            else -> {
                webController = WebControllerImpl()
                webController!!
            }
        }
    }

    init {
        _sources = SourcesImpl.create(this)
    }
}