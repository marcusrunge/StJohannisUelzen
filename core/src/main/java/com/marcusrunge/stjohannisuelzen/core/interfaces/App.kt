package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * Defines the core application interface for managing back press events.
 *
 * This interface provides a mechanism for different components to register as listeners
 * for the back button press, allowing for custom back navigation logic. It allows for
 * a centralized way to intercept and handle the `onBackPressed` event, which is particularly
 * useful in a modularized application where different features might need to control
 * the back stack behavior.
 */
interface App {
    /**
     * Adds a listener for the `onBackPressed` event.
     *
     * When a component (e.g., a Fragment or a custom view) needs to intercept the back press,
     * it can register a listener. The listeners are typically called in the reverse order
     * they were added, allowing the most recently added listener to handle the event first.
     *
     * @param onBackPressedListener The [OnBackPressedListener] to add.
     */
    fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener)

    /**
     * Removes a listener for the `onBackPressed` event.
     *
     * It is important to remove listeners when the component is destroyed to prevent
     * memory leaks and unexpected behavior.
     *
     * @param onBackPressedListener The [OnBackPressedListener] to remove.
     */
    fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener)

    /**
     * Executes the back press interception logic.
     *
     * This method should be called from the main `Activity`'s `onBackPressed()` method.
     * It iterates through the registered listeners and allows them to handle the event.
     * If no listener handles the event, the optional callback is invoked, which typically
     * contains the default back press action (e.g., `super.onBackPressed()`).
     *
     * @param callback A lambda function to be executed if the back press event is not
     *                 handled by any of the registered listeners. Can be null.
     */
    fun onBackPressed(callback: (() -> Unit)?)
}

/**
 * A listener for handling the `onBackPressed` event.
 *
 * Components that need to intercept the back button press should implement this interface
 * and register themselves with the [App] interface.
 */
interface OnBackPressedListener {
    /**
     * Called when the `onBackPressed` event occurs.
     *
     * The implementing component should return `true` if it has handled the back press
     * and the event should not be propagated further. If it returns `false`, the event
     * will be passed to the next listener or the default back press action will be executed.
     *
     * @return `true` if the event was handled, `false` otherwise.
     */
    fun onBackPressed(): Boolean
}
