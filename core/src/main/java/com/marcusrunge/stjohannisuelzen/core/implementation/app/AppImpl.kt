package com.marcusrunge.stjohannisuelzen.core.implementation.app

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.App
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackPressedListener
import java.lang.ref.WeakReference

internal class AppImpl(private val backBase: BackBase) : App {

    internal companion object {
        var app: App? = null
        fun create(backBase: BackBase): App = when {
            app != null -> app!!
            else -> {
                app = AppImpl(backBase)
                app!!
            }
        }
    }

    private val onBackPressedListeners: MutableList<WeakReference<OnBackPressedListener>> =
        mutableListOf()

    override fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.add(WeakReference(onBackPressedListener))
    }

    override fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        val iterator: MutableIterator<WeakReference<OnBackPressedListener>> =
            onBackPressedListeners.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnBackPressedListener> = iterator.next()
            if (weakRef.get() === onBackPressedListener) {
                iterator.remove()
            }
        }
    }

    override fun onBackPressed(callback: (() -> Unit)?) {
        if (!(backBase.web.isWebViewActive && backBase.web.requestCanGoBack())) {
            var handled: Boolean = false
            for (weakRef in onBackPressedListeners) {
                try {
                    if (weakRef.get()?.onBackPressed() == true) handled = true
                } catch (e: Exception) {
                }
            }
            if (!handled) callback?.invoke()
        } else
            backBase.web.requestGoBack()
    }
}