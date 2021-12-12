package com.marcusrunge.stjohannisuelzen.core.implementations.swipe

import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnSwipeListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.Swipe
import java.lang.ref.WeakReference

internal class SwipeImpl(gesturesBase: GesturesBase) : Swipe {
    internal companion object {
        var swipe: Swipe? = null
        fun create(gesturesBase: GesturesBase): Swipe = when {
            swipe != null -> swipe!!
            else -> {
                swipe = SwipeImpl(gesturesBase)
                swipe!!
            }
        }
    }

    private val onSwipeListeners: MutableList<WeakReference<OnSwipeListener>> =
        mutableListOf()

    override fun onSwipe(swipe: com.marcusrunge.stjohannisuelzen.core.enums.Swipe, value: Int) {
        for (weakRef in onSwipeListeners) {
            try {
                weakRef.get()?.onSwipe(swipe, value)
            } catch (e: Exception) {
            }
        }
    }

    override fun addOnSwipeListener(onSwipeListener: OnSwipeListener) {
        onSwipeListeners.add(WeakReference(onSwipeListener))
    }

    override fun removeOnSwipeListener(onSwipeListener: OnSwipeListener) {
        val iterator: MutableIterator<WeakReference<OnSwipeListener>> =
            onSwipeListeners.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnSwipeListener> = iterator.next()
            if (weakRef.get() === onSwipeListener) {
                iterator.remove()
            }
        }
    }
}