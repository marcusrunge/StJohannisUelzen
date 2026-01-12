package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * A central interface providing access to core application functionalities.
 *
 * This interface acts as a hub for accessing different parts of the application's
 * core logic, such as application-level features and web-related functionalities.
 * It is intended to be implemented by a class that aggregates these core components,
 * making them available through a single access point.
 */
interface Back {
    /**
     * Provides access to application-related functionality, such as managing back press events.
     * @see App
     */
    val app: App

    /**
     * Provides access to WebView-related functionality, such as controlling
     * and interacting with WebViews.
     * @see Web
     */
    val web: Web
}
