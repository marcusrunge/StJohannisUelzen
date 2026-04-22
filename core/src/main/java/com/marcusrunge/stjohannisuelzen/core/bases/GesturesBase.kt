package com.marcusrunge.stjohannisuelzen.core.bases

import com.marcusrunge.stjohannisuelzen.core.interfaces.Gestures
import com.marcusrunge.stjohannisuelzen.core.interfaces.Scroll
import com.marcusrunge.stjohannisuelzen.core.interfaces.Swipe

/**
 * An abstract base class that provides a foundational implementation of the [Gestures] interface.
 *
 * This class is designed to be extended by concrete implementations that will provide
 * the specific instances of [Swipe] and [Scroll]. It holds protected backing fields
 * that subclasses are expected to initialize.
 *
 * @param coreBase A reference to the [CoreBase] instance, providing access to the
 *                 application's core infrastructure.
 */
internal abstract class GesturesBase(val coreBase: CoreBase) : Gestures {
    /**
     * The backing field for the [swipe] property.
     * This must be initialized by a subclass.
     * @see [swipe]
     */
    protected lateinit var _swipe: Swipe

    /**
     * The backing field for the [scroll] property.
     * This must be initialized by a subclass.
     * @see [scroll]
     */
    protected lateinit var _scroll: Scroll

    /**
     * Provides access to swipe-related gesture functionality.
     * This implementation returns the instance held in the [_swipe] backing field.
     */
    override val swipe: Swipe
        get() = _swipe

    /**
     * Provides access to scroll-related gesture functionality.
     * This implementation returns the instance held in the [_scroll] backing field.
     */
    override val scroll: Scroll
        get() = _scroll
}
