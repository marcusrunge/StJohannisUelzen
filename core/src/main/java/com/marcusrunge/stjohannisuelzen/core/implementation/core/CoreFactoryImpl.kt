package com.marcusrunge.stjohannisuelzen.core.implementation.core

import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.CoreFactory

class CoreFactoryImpl {
    companion object : CoreFactory{
        private var core : Core? = null
        override fun create(): Core = when {
            core != null -> core!!
            else ->{
                core = CoreImpl()
                core!!
            }
        }
    }
}