package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.bases.WebControllerBase
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.Sources

internal class SourcesImpl(webControllerBase: WebControllerBase) : Sources {

    internal companion object {
        var sources: Sources? = null
        fun create(webControllerBase: WebControllerBase): Sources = when {
            sources != null -> sources!!
            else -> {
                sources = SourcesImpl(webControllerBase)
                sources!!
            }
        }
    }

    override val endpointUrl: String?
        get() = "https://st-johannis-uelzen.wir-e.de/"
}