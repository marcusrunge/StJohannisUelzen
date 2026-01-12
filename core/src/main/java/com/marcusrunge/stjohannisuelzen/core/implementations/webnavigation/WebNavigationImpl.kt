package com.marcusrunge.stjohannisuelzen.core.implementations.webnavigation

import com.marcusrunge.stjohannisuelzen.core.interfaces.OnPageFinishedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnRequestNavigateToListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

/**
 * This class provides the concrete implementation of the [WebNavigation] interface.
 * It is responsible for handling web navigation requests and notifying listeners about page events.
 * The class is designed as a singleton, ensuring that only one instance of [WebNavigationImpl] exists.
 *
 * @see [WebNavigation]
 * @see [OnRequestNavigateToListener]
 * @see [OnPageFinishedListener]
 */
internal class WebNavigationImpl : WebNavigation {

    private var onRequestNavigateToListener: OnRequestNavigateToListener? = null
    private var onPageFinishedListener: OnPageFinishedListener? = null

    /**
     * {@inheritDoc}
     */
    override fun requestNavigateTo(url: String) {
        onRequestNavigateToListener?.onRequestNavigateTo(url)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener) {
        this.onRequestNavigateToListener = onRequestNavigateToListener
    }

    /**
     * {@inheritDoc}
     */
    override fun removeOnRequestNavigateToListener() {
        this.onRequestNavigateToListener = null
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnPageFinishedListener(onPageFinishedListener: OnPageFinishedListener) {
        this.onPageFinishedListener = onPageFinishedListener
    }

    /**
     * {@inheritDoc}
     */
    override fun removeOnPageFinishedListener() {
        this.onPageFinishedListener = null
    }

    /**
     * {@inheritDoc}
     */
    override fun pageFinished() {
        this.onPageFinishedListener?.onPageFinished()
    }

    internal companion object {
        /**
         * Holds the singleton instance of [WebNavigation].
         */
        private var webNavigation: WebNavigation? = null

        /**
         * Creates and provides a singleton instance of [WebNavigation].
         * If an instance does not yet exist, a new one is created.
         *
         * @return The single instance of [WebNavigation].
         */
        fun create(): WebNavigation {
            if (webNavigation == null) {
                webNavigation = WebNavigationImpl()
            }
            return webNavigation!!
        }
    }
}