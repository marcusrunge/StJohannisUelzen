package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackSubscriber
import com.marcusrunge.stjohannisuelzen.core.interfaces.Publisher
import com.marcusrunge.stjohannisuelzen.core.interfaces.Subscriber
import java.lang.ref.WeakReference

internal abstract class BackBase(val coreBase: CoreBase) : Back {
    protected lateinit var _publisher: Publisher
    protected lateinit var _subscriber: Subscriber
    internal val onBackSubscribers: MutableList<WeakReference<OnBackSubscriber>> =
        mutableListOf()
}