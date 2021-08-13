package com.marcusrunge.stjohannisuelzen.core.implementation.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.Publisher

internal class PublisherImpl(val backBase: BackBase) : Publisher {

    internal companion object {
        var publisher: Publisher? = null
        fun create(backBase: BackBase): Publisher = when {
            publisher != null -> publisher!!
            else -> {
                publisher = PublisherImpl(backBase)
                publisher!!
            }
        }
    }

    override fun onBack() {
        for (weakRef in backBase.onBackSubscribers) {
            try {
                weakRef.get()?.onBack()
            } catch (e: Exception) {
            }
        }
    }
}