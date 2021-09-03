package com.marcusrunge.stjohannisuelzen.services

import com.marcusrunge.stjohannisuelzen.interfaces.MainActivityService

internal class MainActivityServiceImpl : MainActivityService {
    internal companion object {
        var mainActivityService: MainActivityService? = null
        fun create(): MainActivityService = when {
            mainActivityService != null -> mainActivityService!!
            else -> {
                mainActivityService = MainActivityServiceImpl()
                mainActivityService!!
            }
        }
    }
}