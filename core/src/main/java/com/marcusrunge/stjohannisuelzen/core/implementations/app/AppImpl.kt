package com.marcusrunge.stjohannisuelzen.core.implementations.app

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.App
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackPressedListener
import java.lang.ref.WeakReference

/**
 * A singleton implementation of the [App] interface.
 *
 * This class manages a list of [OnBackPressedListener]s to allow different components
 * to intercept the back button press. It uses [WeakReference] to hold listeners,
 * preventing memory leaks if a listener is not explicitly removed.
 *
 * @param backBase A reference to [BackBase] to interact with other core components like the web view.
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class AppImpl private constructor(private val backBase: BackBase) : App {

    internal companion object {
        @Volatile
        private var instance: App? = null

        /**
         * Creates and returns the singleton instance of [App].
         *
         * This method uses a thread-safe, double-checked locking pattern to ensure that
         * only one instance of [AppImpl] is created.
         *
         * @param backBase The [BackBase] instance required for initialization.
         * @return The singleton [App] instance.
         */
        fun create(backBase: BackBase): App =
            instance ?: synchronized(this) {
                instance ?: AppImpl(backBase).also { instance = it }
            }
    }

    /**
     * A list of weakly-referenced listeners for the `onBackPressed` event.
     * Using [WeakReference] prevents memory leaks by allowing garbage collection of listeners
     * that are no longer in use.
     */
    private val onBackPressedListeners: MutableList<WeakReference<OnBackPressedListener>> =
        mutableListOf()

    /**
     * Registers a listener for the `onBackPressed` event.
     *
     * @param onBackPressedListener The listener to add.
     */
    override fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.add(WeakReference(onBackPressedListener))
    }

    /**
     * Unregisters a listener for the `onBackPressed` event.
     *
     * This method iterates through the listeners and removes the one that matches the
     * provided instance. It also cleans up any stale `WeakReference`s it encounters.
     *
     * @param onBackPressedListener The listener to remove.
     */
    override fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.removeAll { it.get() == null || it.get() === onBackPressedListener }
    }

    /**
     * Executes the back press interception logic.
     *
     * This method first checks if an active web view can navigate backward. If so, it requests
     * the web view to go back. Otherwise, it iterates through the registered listeners in
     * reverse order (LIFO) and allows them to handle the event. If no listener handles it,
     * the optional `callback` is invoked.
     *
     * @param callback A lambda to be executed if the back press is not handled by any listener.
     */
    override fun onBackPressed(callback: (() -> Unit)?) {
        if (backBase.web.isWebViewActive && backBase.web.requestCanGoBack()) {
            backBase.web.requestGoBack()
            return
        }

        // Clean up stale references and check if any listener handles the event.
        onBackPressedListeners.removeAll { it.get() == null }
        val handled = onBackPressedListeners.asReversed().any { weakRef ->
            try {
                weakRef.get()?.onBackPressed() == true
            } catch (e: Exception) {
                // To prevent a faulty listener from crashing the app, we ignore exceptions.
                false
            }
        }

        if (!handled) {
            callback?.invoke()
        }
    }
}
