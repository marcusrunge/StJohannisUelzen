package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.*
import java.lang.ref.WeakReference

internal abstract class BackBase(val coreBase: CoreBase) : Back {
    protected lateinit var _app: App
    protected lateinit var _web: Web
}