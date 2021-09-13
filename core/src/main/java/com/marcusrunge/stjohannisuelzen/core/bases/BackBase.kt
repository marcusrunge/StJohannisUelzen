package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.App
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Web

internal abstract class BackBase(val coreBase: CoreBase) : Back {
    protected lateinit var _app: App
    protected lateinit var _web: Web
}