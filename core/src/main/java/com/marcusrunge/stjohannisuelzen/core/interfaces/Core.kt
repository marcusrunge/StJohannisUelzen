package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Core {
    /**
     * Provides api keys related functionality.
     */
    val apiKeys:ApiKeys

    /**
     * Provides back button related functionality.
     */
    val back: Back

    /**
     * Provides web navigation related functionality.
     */
    val webNavigation: WebNavigation

    /**
     * Provides gestures related functionality.
     */
    val gestures: Gestures
}