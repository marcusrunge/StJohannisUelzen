package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * An interface for providing API keys for various services.
 * This interface is intended to be implemented by a class that can securely
 * retrieve and provide the necessary API keys for different parts of the application.
 */
interface ApiKeys {
    /**
     * The API key for accessing Google Maps services.
     * This key is used for displaying maps and other location-based features.
     */
    var googleMaps: String

    /**
     * The API key for accessing YouTube Data API services.
     * This key is used for fetching and displaying YouTube videos or playlists.
     */
    var youtube: String
}
