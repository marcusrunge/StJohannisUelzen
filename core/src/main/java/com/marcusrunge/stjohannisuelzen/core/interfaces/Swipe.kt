package com.marcusrunge.stjohannisuelzen.core.interfaces

import com.marcusrunge.stjohannisuelzen.core.enums.Swipe as SwipeDirection

/**
 * An interface for managing swipe events and listeners.
 *
 * This interface provides a centralized mechanism for dispatching swipe events
 * to registered listeners. It allows components to be notified when a swipe
 * action occurs, abstracting the source of the swipe event.
 */
interface Swipe {
    /**
     * Dispatches a swipe event to all registered listeners.
     *
     * This method is typically called by a gesture detector when a swipe
     * event is detected.
     *
     * @param swipe The direction of the swipe, as defined by the [SwipeDirection] enum.
     * @param value The distance of the swipe in pixels.
     */
    fun onSwipe(swipe: SwipeDirection, value: Int)

    /**
     * Registers a listener to receive swipe events.
     *
     * Listeners will be notified when the [onSwipe] method is called.
     *
     * @param onSwipeListener The [OnSwipeListener] to add.
     */
    fun addOnSwipeListener(onSwipeListener: OnSwipeListener)

    /**
     * Unregisters a listener, so it no longer receives swipe events.
     *
     * It's important to remove listeners when they are no longer needed (e.g., in a
     * component's lifecycle destruction method) to prevent memory leaks.
     *
     * @param onSwipeListener The [OnSwipeListener] to remove.
     */
    fun removeOnSwipeListener(onSwipeListener: OnSwipeListener)
}

/**
 * A listener for receiving swipe events.
 *
 * Components that need to react to swipe events should implement this interface
 * and register themselves with the [Swipe] interface.
 */
interface OnSwipeListener {
    /**
     * Called when a swipe event occurs.
     *
     * @param swipe The direction of the swipe (e.g., left or right).
     * @param value The distance of the swipe in pixels.
     */
    fun onSwipe(swipe: SwipeDirection, value: Int)
}
