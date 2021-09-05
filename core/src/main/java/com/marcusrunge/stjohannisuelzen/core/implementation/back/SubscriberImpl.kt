package com.marcusrunge.stjohannisuelzen.core.implementation.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackSubscriber
import com.marcusrunge.stjohannisuelzen.core.interfaces.Subscriber
import java.lang.ref.WeakReference

internal class SubscriberImpl(private val backBase: BackBase) : Subscriber {

    internal companion object {
        var subscriber: Subscriber? = null
        fun create(backBase: BackBase): Subscriber = when {
            subscriber != null -> subscriber!!
            else -> {
                subscriber = SubscriberImpl(backBase)
                subscriber!!
            }
        }
    }

    override fun add(onBackSubscriber: OnBackSubscriber) {
        backBase.onBackSubscribers.add(WeakReference(onBackSubscriber))
    }

    override fun remove(onBackSubscriber: OnBackSubscriber) {
        val iterator: MutableIterator<WeakReference<OnBackSubscriber>> =
            backBase.onBackSubscribers.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnBackSubscriber> = iterator.next()
            if (weakRef.get() === onBackSubscriber) {
                iterator.remove()
            }
        }
    }
}