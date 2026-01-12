package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.ApiKeys
import com.marcusrunge.stjohannisuelzen.core.interfaces.Back
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

/**
 * An abstract base class that provides a foundational implementation of the [Core] interface.
 *
 * This class is designed to be extended by concrete implementations that will provide the
 * specific instances for the core functionalities like [ApiKeys], [Back], [WebNavigation],
 * and [Gestures]. It holds protected backing fields that subclasses are expected to initialize.
 */
internal abstract class CoreBase : Core {
    /**
     * The backing field for the [apiKeys] property.
     * This must be initialized by a subclass.
     * @see [apiKeys]
     */
    protected lateinit var _apiKeys: ApiKeys

    /**
     * The backing field for the [back] property.
     * This must be initialized by a subclass.
     * @see [back]
     */
    protected lateinit var _back: Back

    /**
     * The backing field for the [webNavigation] property.
     * This must be initialized by a subclass.
     * @see [webNavigation]
     */
    protected lateinit var _webNavigation: WebNavigation

    /**
     * The backing field for the [gestures] property.
     * This must be initialized by a subclass.
     * @see [gestures]
     */
    protected lateinit var _gestures: Gestures

    /**
     * Provides access to API key management.
     * This implementation returns the instance held in the [_apiKeys] backing field.
     */
    override val apiKeys: ApiKeys
        get() = _apiKeys

    /**
     * Provides access to back press handling and other app-level functionalities.
     * This implementation returns the instance held in the [_back] backing field.
     */
    override val back: Back
        get() = _back

    /**
     * Provides access to web navigation controls.
     * This implementation returns the instance held in the [_webNavigation] backing field.
     */
    override val webNavigation: WebNavigation
        get() = _webNavigation

    /**
     * Provides access to gesture detection and handling logic.
     * This implementation returns the instance held in the [_gestures] backing field.
     */
    override val gestures: Gestures
        get() = _gestures
}
