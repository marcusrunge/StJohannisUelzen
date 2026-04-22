package com.marcusrunge.stjohannisuelzen.core.implementations.gestures

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.implementations.scroll.ScrollImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.swipe.SwipeImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures

/**
 * A singleton implementation of the [Gestures] interface.
 *
 * This class provides concrete implementations for the [Swipe] and [Scroll] interfaces,
 * which are essential components of the application's gesture handling system. It follows the
 * singleton pattern to ensure that only one instance of these core services exists.
 *
 * @param coreBase A reference to [CoreBase], providing access to the application's
 *                 core infrastructure.
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class GesturesImpl private constructor(coreBase: CoreBase) : GesturesBase(coreBase) {
    /**
     * The companion object is responsible for creating and providing the singleton instance
     * of [GesturesImpl].
     */
    internal companion object {
        @Volatile
        private var instance: Gestures? = null

        /**
         * Creates and returns the singleton instance of [Gestures].
         *
         * This method uses a thread-safe, double-checked locking pattern to ensure that
         * only one instance of [GesturesImpl] is created.
         *
         * @param coreBase The [CoreBase] instance required for initialization.
         * @return The singleton [Gestures] instance.
         */
        fun create(coreBase: CoreBase): Gestures =
            instance ?: synchronized(this) {
                instance ?: GesturesImpl(coreBase).also { instance = it }
            }
    }

    /**
     * Initializes the [_swipe] and [_scroll] properties with their concrete implementations.
     */
    init {
        _swipe = SwipeImpl.create(this)
        _scroll = ScrollImpl.create(this)
    }
}
