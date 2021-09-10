package com.marcusrunge.stjohannisuelzen.core.interfaces

interface CoreFactory {
    /**
     * Creates Core singleton instance
     */
    fun create(): Core
}