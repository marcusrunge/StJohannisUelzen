package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Swipe {
    /**
     * Executes the onSwipe detection.
     * @param swipe the direction.
     * @param value the amount of swiped pixels.
     */
    fun onSwipe(swipe: com.marcusrunge.stjohannisuelzen.core.enums.Swipe, value: Int)

    /**
     * Adds listener for the onSwipe event.
     * @param onSwipeListener the listener.
     */
    fun addOnSwipeListener(onSwipeListener: OnSwipeListener)

    /**
     * Removes listener for the onSwipe event.
     * @param onSwipeListener the listener.
     */
    fun removeOnSwipeListener(onSwipeListener: OnSwipeListener)
}

interface OnSwipeListener {
    /**
     * Occurs when the onSwipe event happens.
     * @param swipe the direction.
     * @param value the amount of scrolled pixels.
     */
    fun onSwipe(swipe: com.marcusrunge.stjohannisuelzen.core.enums.Swipe, value: Int)
}