package com.marcusrunge.stjohannisuelzen.core.interfaces

import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

interface CoreFactory {
    /**
     * Creates Core singleton instance
     */
    fun create(webController: WebController): Core
}