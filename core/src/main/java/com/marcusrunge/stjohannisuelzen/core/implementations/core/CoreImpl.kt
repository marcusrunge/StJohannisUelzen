package com.marcusrunge.stjohannisuelzen.core.implementations.core

import com.marcusrunge.stjohannisuelzen.core.bases.CoreBase
import com.marcusrunge.stjohannisuelzen.core.implementations.apiKeys.ApiKeysImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.back.BackImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.gestures.GesturesImpl
import com.marcusrunge.stjohannisuelzen.core.implementations.webnavigation.WebNavigationImpl

/**
 * A concrete implementation of the [CoreBase] class, providing the central hub for all core functionalities.
 *
 * This internal class is the main implementation of the [com.marcusrunge.stjohannisuelzen.core.interfaces.Core]
 * interface. It is responsible for instantiating and holding references to all the essential
 * components of the core module, such as API key management, back navigation, web navigation,
 * and gesture handling.
 *
 * This class is instantiated as a singleton by [CoreFactoryImpl].
 */
internal class CoreImpl : CoreBase() {
    /**
     * Initializes the core components.
     *
     * This block sets up all the necessary services by creating instances of their
     * concrete implementations and assigning them to the corresponding properties
     * inherited from [CoreBase].
     */
    init {
        _apiKeys = ApiKeysImpl.create()
        _back = BackImpl.create(this)
        _webNavigation = WebNavigationImpl.create()
        _gestures = GesturesImpl.create(this)
    }
}
