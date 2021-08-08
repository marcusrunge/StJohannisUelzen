package com.marcusrunge.stjohannisuelzen.webcontroller.implementation

import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebControllerFactory

class WebControllerFactoryImpl {
    companion object : WebControllerFactory{
        private var webController : WebController? = null
        override fun create(): WebController = when {
            webController != null -> webController!!
            else ->{
                webController = WebControllerImpl()
                webController!!
            }
        }

    }
}