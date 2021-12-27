package com.marcusrunge.stjohannisuelzen.core.interfaces

interface App {
    /**
     * Adds listener for the onBackPressed event.
     * @param onBackPressedListener The listener.
     */
    fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener)

    /**
     * Removes listener for the onBackPressed event.
     * @param onBackPressedListener The listener.
     */
    fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener)

    /**
     * Executes the onBackPressed interception.
     */
    fun onBackPressed(callback: (() -> Unit)?)
}

interface OnBackPressedListener {
    /**
     * Occurs when the onBackPressed event should be handled by the listener.
     * @return a value whether the event was handled by the listener.
     */
    fun onBackPressed(): Boolean
}