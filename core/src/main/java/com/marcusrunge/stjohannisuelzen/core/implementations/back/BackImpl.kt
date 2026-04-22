package com.marcusrunge.stjohannisuelzen.core.implementations.back

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementations.app.AppImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.web.WebImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back

/**
 * A singleton implementation of the [Back] interface.
 *
 * This class provides concrete implementations for the [App] and [Web] interfaces,
 * which are essential components of the application's core logic. It follows the
 * singleton pattern to ensure that only one instance of these core services exists.
 *
 * @param coreBase A reference to [CoreBase], providing access to the application's
 *                 core infrastructure.
 * @constructor The private constructor enforces the singleton pattern.
 */
internal class BackImpl private constructor(coreBase: CoreBase) : BackBase(coreBase) {
    /**
     * The companion object is responsible for creating and providing the singleton instance
     * of [BackImpl].
     */
    internal companion object {
        @Volatile
        private var instance: Back? = null

        /**
         * Creates and returns the singleton instance of [Back].
         *
         * This method uses a thread-safe, double-checked locking pattern to ensure that
         * only one instance of [BackImpl] is created.
         *
         * @param coreBase The [CoreBase] instance required for initialization.
         * @return The singleton [Back] instance.
         */
        fun create(coreBase: CoreBase): Back =
            instance ?: synchronized(this) {
                instance ?: BackImpl(coreBase).also { instance = it }
            }
    }

    /**
     * Initializes the [_app] and [_web] properties with their concrete implementations.
     */
    init {
        _app = AppImpl.create(this)
        _web = WebImpl.create(this)
    }
}
