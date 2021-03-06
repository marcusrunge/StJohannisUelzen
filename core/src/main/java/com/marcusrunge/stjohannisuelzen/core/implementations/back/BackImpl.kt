package com.marcusrunge.stjohannisuelzen.core.implementations.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementations.app.AppImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.web.WebImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.App
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Web

internal class BackImpl(coreBase: CoreBase) : BackBase(coreBase) {
    internal companion object {
        var back: Back? = null
        fun create(coreBase: CoreBase): Back = when {
            back != null -> back!!
            else -> {
                back = BackImpl(coreBase)
                back!!
            }
        }
    }

    init {
        _app = AppImpl.create(this)
        _web = WebImpl.create(this)
    }

    override val app: App
        get() = _app
    override val web: Web
        get() = _web
}