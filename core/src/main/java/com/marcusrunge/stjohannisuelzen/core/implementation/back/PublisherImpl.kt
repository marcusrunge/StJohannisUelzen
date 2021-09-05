package com.marcusrunge.stjohannisuelzen.core.implementation.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.Publisher

internal class PublisherImpl(private val backBase: BackBase) : Publisher {

    internal companion object {
        private var publisher: Publisher? = null
        fun create(backBase: BackBase): Publisher = when {
            publisher != null -> publisher!!
            else -> {
                publisher = PublisherImpl(backBase)
                publisher!!
            }
        }
    }

    override fun onBack(callback: (() -> Unit)?) {
        if (!(backBase.coreBase.webController.control.isWebViewActive && backBase.coreBase.webController.control.requestCanGoBack())) {
            var handled: Boolean = false
            for (weakRef in backBase.onBackSubscribers) {
                try {
                    if (weakRef.get()?.onBack() == true) handled = true
                } catch (e: Exception) {
                }
            }
            if (!handled) callback?.invoke()
        } else
            backBase.coreBase.webController.control.goBack()
    }
}