package com.marcusrunge.stjohannisuelzen.webcontroller.interfaces

interface WebControllerFactory {

    /**
     * Creates WebController singleton instance
     */
    fun create(): WebController
}