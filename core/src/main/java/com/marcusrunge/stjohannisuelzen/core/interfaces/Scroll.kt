package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Scroll {
    /**
     * Executes the onScroll detection.
     * @param scroll the direction.
     * @param value the amount of scrolled pixels.
     */
    fun onScroll(scroll: com.marcusrunge.stjohannisuelzen.core.enums.Scroll, value: Int)

    /**
     * Adds listener for the onScroll event.
     * @param onScrollListener the listener.
     */
    fun addOnScrollListener(onScrollListener: OnScrollListener)

    /**
     * Removes listener for the onScoll event.
     * @param onScrollListener the listener.
     */
    fun removeOnScrollListener(onScrollListener: OnScrollListener)
}

interface OnScrollListener {
    /**
     * Occurs when the onScroll event happens.
     * @param scroll the direction.
     * @param value the amount of scrolled pixels.
     */
    fun onScroll(scroll: com.marcusrunge.stjohannisuelzen.core.enums.Scroll, value: Int)
}