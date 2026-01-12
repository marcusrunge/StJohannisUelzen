package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * An interface for accessing gesture-related functionalities.
 *
 * This interface provides a centralized way to access different types of gesture
 * handling logic, such as swipe and scroll events. It is designed to be implemented
 * by a class that aggregates various gesture-related components.
 */
interface Gestures {
    /**
     * Provides access to swipe-related gesture functionality.
     * This typically includes handling horizontal and vertical swipe gestures.
     * @see Swipe
     */
    val swipe: Swipe

    /**
     * Provides access to scroll-related gesture functionality.
     * This typically includes handling scroll events on views.
     * @see Scroll
     */
    val scroll: Scroll
}
