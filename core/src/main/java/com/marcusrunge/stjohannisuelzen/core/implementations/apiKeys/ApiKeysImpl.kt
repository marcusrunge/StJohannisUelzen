package com.marcusrunge.stjohannisuelzen.core.implementations.apiKeys

import com.marcusrunge.stjohannisuelzen.core.interfaces.ApiKeys

/**
 * A singleton implementation of the [ApiKeys] interface.
 *
 * This class provides a single, centralized place to store and access API keys for
 * services like Google Maps and YouTube. It is designed to be a singleton to ensure
 * that there's only one instance of the API key storage throughout the application.
 *
 * The class uses `lateinit` for its properties, which are expected to be set
 * at application startup.
 *
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class ApiKeysImpl private constructor() : ApiKeys {

    /**
     * The companion object is responsible for creating and providing the singleton instance
     * of [ApiKeysImpl].
     */
    internal companion object {
        private val instance: ApiKeys by lazy { ApiKeysImpl() }

        /**
         * Creates and returns the singleton instance of [ApiKeys].
         *
         * The `lazy` delegate ensures that the instance is created only when it's first
         * accessed and that the initialization is thread-safe.
         *
         * @return The singleton [ApiKeys] instance.
         */
        fun create(): ApiKeys = instance
    }

    /**
     * The API key for accessing Google Maps services.
     * This key is used for displaying maps and other location-based features.
     * This property must be initialized before use.
     */
    override lateinit var googleMaps: String

    /**
     * The API key for accessing YouTube Data API services.
     * This key is used for fetching and displaying YouTube videos or playlists.
     * This property must be initialized before use.
     */
    override lateinit var youtube: String
}
