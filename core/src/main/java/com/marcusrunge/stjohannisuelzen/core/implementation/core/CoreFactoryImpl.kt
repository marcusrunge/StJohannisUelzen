package com.marcusrunge.stjohannisuelzen.core.implementation.core

import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.CoreFactory
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController

class CoreFactoryImpl {
    companion object : CoreFactory{
        private var core : Core? = null
        override fun create(webController: WebController): Core = when {
            core != null -> core!!
            else ->{
                core = CoreImpl(webController)
                core!!
            }
        }
    }
}