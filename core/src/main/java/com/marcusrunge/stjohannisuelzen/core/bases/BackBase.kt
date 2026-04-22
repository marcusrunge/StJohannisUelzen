package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.App
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Web

/**
 * An abstract base class that provides a foundational implementation of the [Back] interface.
 *
 * This class is designed to be extended by concrete implementations that will provide
 * the specific instances of [App] and [Web]. It holds protected backing fields
 * that subclasses are expected to initialize.
 *
 * @param coreBase A reference to the [CoreBase] instance, providing access to the
 *                 application's core infrastructure.
 */
internal abstract class BackBase(val coreBase: CoreBase) : Back {
    /**
     * The backing field for the [app] property.
     * This must be initialized by a subclass.
     * @see [app]
     */
    protected lateinit var _app: App

    /**
     * The backing field for the [web] property.
     * This must be initialized by a subclass.
     * @see [web]
     */
    protected lateinit var _web: Web

    /**
     * Provides access to application-related functionality.
     * This implementation returns the instance held in the [_app] backing field.
     */
    override val app: App
        get() = _app

    /**
     * Provides access to WebView-related functionality.
     * This implementation returns the instance held in the [_web] backing field.
     */
    override val web: Web
        get() = _web
}
