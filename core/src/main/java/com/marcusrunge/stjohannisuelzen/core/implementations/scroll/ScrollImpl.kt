package com.marcusrunge.stjohannisuelzen.core.implementations.scroll

import com.marcusrunge.stjohannisuelzen.core.bases.GesturesBase
import com.marcusrunge.stjohannisuelzen.core.enums.Scroll as ScrollDirection
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnScrollListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.Scroll
import java.lang.ref.WeakReference

/**
 * A singleton implementation of the [Scroll] interface.
 *
 * This class manages a list of [OnScrollListener]s to allow different components
 * to be notified of scroll events. It uses [WeakReference] to hold listeners,
 * preventing memory leaks if a listener is not explicitly removed.
 *
 * @param gesturesBase A reference to [GesturesBase], though it is not used in this implementation.
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class ScrollImpl private constructor(gesturesBase: GesturesBase) : Scroll {

    /**
     * The companion object is responsible for creating and providing the singleton instance
     * of [ScrollImpl].
     */
    internal companion object {
        @Volatile
        private var instance: Scroll? = null

        /**
         * Creates and returns the singleton instance of [Scroll].
         *
         * This method uses a thread-safe, double-checked locking pattern to ensure that
         * only one instance of [ScrollImpl] is created.
         *
         * @param gesturesBase The [GesturesBase] instance required for initialization.
         * @return The singleton [Scroll] instance.
         */
        fun create(gesturesBase: GesturesBase): Scroll =
            instance ?: synchronized(this) {
                instance ?: ScrollImpl(gesturesBase).also { instance = it }
            }
    }

    /**
     * A list of weakly-referenced listeners for scroll events.
     * Using [WeakReference] prevents memory leaks by allowing garbage collection of listeners
     * that are no longer in use.
     */
    private val onScrollListeners: MutableList<WeakReference<OnScrollListener>> =
        mutableListOf()

    /**
     * Dispatches a scroll event to all registered listeners.
     *
     * This method iterates through the list of listeners and invokes their `onScroll` method.
     * It also cleans up any stale `WeakReference`s it encounters.
     *
     * @param scroll The direction of the scroll.
     * @param value The distance of the scroll in pixels.
     */
    override fun onScroll(scroll: ScrollDirection, value: Int) {
        onScrollListeners.removeAll { it.get() == null } // Clean up stale references
        for (weakRef in onScrollListeners) {
            try {
                weakRef.get()?.onScroll(scroll, value)
            } catch (e: Exception) {
                // To prevent a faulty listener from crashing the app, we ignore exceptions.
            }
        }
    }

    /**
     * Registers a listener to receive scroll events.
     *
     * @param onScrollListener The listener to add.
     */
    override fun addOnScrollListener(onScrollListener: OnScrollListener) {
        onScrollListeners.add(WeakReference(onScrollListener))
    }

    /**
     * Unregisters a listener, so it no longer receives scroll events.
     *
     * This method removes the specified listener from the list. It also cleans up any
     * stale `WeakReference`s it encounters.
     *
     * @param onScrollListener The listener to remove.
     */
    override fun removeOnScrollListener(onScrollListener: OnScrollListener) {
        onScrollListeners.removeAll { it.get() == null || it.get() === onScrollListener }
    }
}
