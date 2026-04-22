package com.marcusrunge.stjohannisuelzen.core.interfaces

import com.marcusrunge.stjohannisuelzen.core.enums.Scroll as ScrollDirection

/**
 * An interface for managing scroll events and listeners.
 *
 * This interface provides a centralized mechanism for dispatching scroll events
 * to registered listeners. It allows components to be notified when a scroll
 * action occurs, abstracting the source of the scroll event.
 */
interface Scroll {
    /**
     * Dispatches a scroll event to all registered listeners.
     *
     * This method is typically called by a gesture detector when a scroll
     * event is detected.
     *
     * @param scroll The direction of the scroll, as defined by the [ScrollDirection] enum.
     * @param value The distance of the scroll in pixels.
     */
    fun onScroll(scroll: ScrollDirection, value: Int)

    /**
     * Registers a listener to receive scroll events.
     *
     * Listeners will be notified when the [onScroll] method is called.
     *
     * @param onScrollListener The [OnScrollListener] to add.
     */
    fun addOnScrollListener(onScrollListener: OnScrollListener)

    /**
     * Unregisters a listener, so it no longer receives scroll events.
     *
     * It's important to remove listeners when they are no longer needed (e.g., in a
     * component's lifecycle destruction method) to prevent memory leaks.
     *
     * @param onScrollListener The [OnScrollListener] to remove.
     */
    fun removeOnScrollListener(onScrollListener: OnScrollListener)
}

/**
 * A listener for receiving scroll events.
 *
 * Components that need to react to scroll events should implement this interface
 * and register themselves with the [Scroll] interface.
 */
interface OnScrollListener {
    /**
     * Called when a scroll event occurs.
     *
     * @param scroll The direction of the scroll (e.g., up or down).
     * @param value The distance of the scroll in pixels.
     */
    fun onScroll(scroll: ScrollDirection, value: Int)
}
