package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * An interface for managing web navigation events between different components.
 *
 * This interface provides a decoupled way for a component to request web navigation
 * and to be notified when a web page has finished loading, without having a direct
 * reference to the WebView implementation.
 */
interface WebNavigation {
    /**
     * Sends a request to navigate to a specific URL.
     *
     * This method is called by a component that wants to trigger a web navigation event.
     * The actual navigation will be handled by a listener.
     *
     * @param url The URL of the website to navigate to.
     */
    fun requestNavigateTo(url: String)

    /**
     * Registers a listener for handling URL navigation requests.
     *
     * The provided listener will be invoked when [requestNavigateTo] is called.
     *
     * @param onRequestNavigateToListener The [OnRequestNavigateToListener] to register.
     */
    fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener)

    /**
     * Removes the currently registered [OnRequestNavigateToListener].
     *
     * It's important to remove the listener when it's no longer needed to prevent
     * memory leaks.
     */
    fun removeOnRequestNavigateToListener()

    /**
     * Registers a listener to be notified when a web page has finished loading.
     *
     * @param onPageFinishedListener The [OnPageFinishedListener] to register.
     */
    fun setOnPageFinishedListener(onPageFinishedListener: OnPageFinishedListener)

    /**
     * Removes the currently registered [OnPageFinishedListener].
     *
     * It's important to remove the listener when it's no longer needed to prevent
     * memory leaks.
     */
    fun removeOnPageFinishedListener()

    /**
     * Signals that the web page loading has finished.
     *
     * This method should be called by the web view implementation when the
     * `onPageFinished` event occurs. It will then notify the registered
     * [OnPageFinishedListener].
     */
    fun pageFinished()
}

/**
 * A listener for handling web navigation requests.
 *
 * Implement this interface in the component that is responsible for handling
 * the actual web navigation (e.g., a Fragment containing a WebView).
 */
interface OnRequestNavigateToListener {
    /**
     * Called when a component requests to navigate to a specific URL.
     *
     * @param url The URL of the website to navigate to.
     */
    fun onRequestNavigateTo(url: String)
}

/**
 * A listener to be notified when a web page has finished loading.
 *
 * Implement this interface in components that need to react to the completion
 * of a web page load.
 */
interface OnPageFinishedListener {
    /**
     * Called when the loading of a web page has finished.
     */
    fun onPageFinished()
}
