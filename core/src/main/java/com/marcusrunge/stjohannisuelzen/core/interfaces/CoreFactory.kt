package com.marcusrunge.stjohannisuelzen.core.interfaces

/**
 * A factory interface for creating instances of the [Core] interface.
 *
 * This factory is responsible for providing a singleton instance of the [Core] interface,
 * which serves as the main entry point for all core functionalities of the application.
 * Using a factory pattern here allows for dependency injection and easy testing,
 * as different implementations of [Core] can be provided.
 */
interface CoreFactory {
    /**
     * Creates and returns a singleton instance of the [Core] interface.
     *
     * This method should be implemented to provide a single, consistent instance of [Core]
     * throughout the application's lifecycle.
     *
     * @return The singleton [Core] instance.
     */
    fun create(): Core
}
