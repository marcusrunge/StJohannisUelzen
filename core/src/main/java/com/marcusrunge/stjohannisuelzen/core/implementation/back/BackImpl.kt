package com.marcusrunge.stjohannisuelzen.core.implementation.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Publisher
import com.marcusrunge.stjohannisuelzen.core.interfaces.Subscriber

internal class BackImpl : BackBase() {
    internal companion object {
        var back: Back? = null
        fun create(): Back = when {
            back != null -> back!!
            else -> {
                back = BackImpl()
                back!!
            }
        }
    }

    init {
        _publisher = PublisherImpl.create(this)
        _subscriber = SubscriberImpl.create(this)
    }

    override val subscriber: Subscriber
        get() = _subscriber
    override val publisher: Publisher
        get() = _publisher
}