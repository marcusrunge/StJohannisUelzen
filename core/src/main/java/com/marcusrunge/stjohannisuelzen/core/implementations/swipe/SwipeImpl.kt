package com.marcusrunge.stjohannisuelzen.core.implementations.swipe

import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.enums.Swipe as SwipeDirection
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnSwipeListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.Swipe
import java.lang.ref.WeakReference

/**
 * A singleton implementation of the [Swipe] interface.
 *
 * This class manages a list of [OnSwipeListener]s to allow different components
 * to be notified of swipe events. It uses [WeakReference] to hold listeners,
 * preventing memory leaks if a listener is not explicitly removed.
 *
 * @param gesturesBase A reference to [GesturesBase], though it is not used in this implementation.
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class SwipeImpl private constructor(gesturesBase: GesturesBase) : Swipe {

    /**
     * The companion object is responsible for creating and providing the singleton instance
     * of [SwipeImpl].
     */
    internal companion object {
        @Volatile
        private var instance: Swipe? = null

        /**
         * Creates and returns the singleton instance of [Swipe].
         *
         * This method uses a thread-safe, double-checked locking pattern to ensure that
         * only one instance of [SwipeImpl] is created.
         *
         * @param gesturesBase The [GesturesBase] instance required for initialization.
         * @return The singleton [Swipe] instance.
         */
        fun create(gesturesBase: GesturesBase): Swipe =
            instance ?: synchronized(this) {
                instance ?: SwipeImpl(gesturesBase).also { instance = it }
            }
    }

    /**
     * A list of weakly-referenced listeners for swipe events.
     * Using [WeakReference] prevents memory leaks by allowing garbage collection of listeners
     * that are no longer in use.
     */
    private val onSwipeListeners: MutableList<WeakReference<OnSwipeListener>> =
        mutableListOf()

    /**
     * Dispatches a swipe event to all registered listeners.
     *
     * This method iterates through the list of listeners and invokes their `onSwipe` method.
     * It also cleans up any stale `WeakReference`s it encounters.
     *
     * @param swipe The direction of the swipe.
     * @param value The distance of the swipe in pixels.
     */
    override fun onSwipe(swipe: SwipeDirection, value: Int) {
        onSwipeListeners.removeAll { it.get() == null } // Clean up stale references
        for (weakRef in onSwipeListeners) {
            try {
                weakRef.get()?.onSwipe(swipe, value)
            } catch (e: Exception) {
                // To prevent a faulty listener from crashing the app, we ignore exceptions.
            }
        }
    }

    /**
     * Registers a listener to receive swipe events.
     *
     * @param onSwipeListener The listener to add.
     */
    override fun addOnSwipeListener(onSwipeListener: OnSwipeListener) {
        onSwipeListeners.add(WeakReference(onSwipeListener))
    }

    /**
     * Unregisters a listener, so it no longer receives swipe events.
     *
     * This method removes the specified listener from the list. It also cleans up any
     * stale `WeakReference`s it encounters.
     *
     * @param onSwipeListener The listener to remove.
     */
    override fun removeOnSwipeListener(onSwipeListener: OnSwipeListener) {
        onSwipeListeners.removeAll { it.get() == null || it.get() === onSwipeListener }
    }
}
