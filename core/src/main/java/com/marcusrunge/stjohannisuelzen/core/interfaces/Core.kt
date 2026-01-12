package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * The central interface for accessing all core functionalities of the application.
 *
 * This interface acts as a single entry point to various core modules, such as API keys,
 * back navigation, web navigation, and gesture handling. It follows the facade pattern
 * to provide a simplified interface to a larger body of code.
 */
interface Core {
    /**
     * Provides access to API key management.
     * @see ApiKeys
     */
    val apiKeys: ApiKeys

    /**
     * Provides access to back press handling and other app-level functionalities.
     * @see Back
     */
    val back: Back

    /**
     * Provides access to web navigation controls.
     * @see WebNavigation
     */
    val webNavigation: WebNavigation

    /**
     * Provides access to gesture detection and handling logic.
     * @see Gestures
     */
    val gestures: Gestures
}
