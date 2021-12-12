package com.marcusrunge.stjohannisuelzen.core.implementations.scroll

import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnScrollListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.Scroll
import java.lang.ref.WeakReference

internal class ScrollImpl(gesturesBase: GesturesBase) : Scroll {
    internal companion object {
        var swipe: Scroll? = null
        fun create(gesturesBase: GesturesBase): Scroll = when {
            swipe != null -> swipe!!
            else -> {
                swipe = ScrollImpl(gesturesBase)
                swipe!!
            }
        }
    }

    private val onScrollListeners: MutableList<WeakReference<OnScrollListener>> =
        mutableListOf()

    override fun onScroll(scroll: com.marcusrunge.stjohannisuelzen.core.enums.Scroll, value: Int) {
        for (weakRef in onScrollListeners) {
            try {
                weakRef.get()?.onScroll(scroll, value)
            } catch (e: Exception) {
            }
        }
    }

    override fun addOnScrollListener(onScrollListener: OnScrollListener) {
        onScrollListeners.add(WeakReference(onScrollListener))
    }

    override fun removeOnScrollListener(onScrollListener: OnScrollListener) {
        val iterator: MutableIterator<WeakReference<OnScrollListener>> =
            onScrollListeners.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnScrollListener> = iterator.next()
            if (weakRef.get() === onScrollListener) {
                iterator.remove()
            }
        }
    }
}