package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Web {
    /**
     * Gets or sets a value whether the webview is active.
     */
    var isWebViewActive: Boolean

    /**
     * Requests go back.
     */
    fun requestGoBack()

    /**
     * Requests whether can go back.
     * @return whether can go back.
     */
    fun requestCanGoBack(): Boolean

    /**
     * Sets an OnGoBackRequestedListener.
     * @param onGoBackRequestedListener the listener.
     */
    fun setOnGoBackRequestedListener(onGoBackRequestedListener: OnGoBackRequestedListener)

    /**
     * Removes the listener
     */
    fun removeOnGoBackRequestedListener()

    /**
     * Sets an OnCanGoBackRequestedListener,
     * @param onCanGoBackRequestedListener the listener.
     */
    fun setOnWebCanGoBackRequestedListener(onCanGoBackRequestedListener: OnCanGoBackRequestedListener)

    /**
     * Removes the listener.
     */
    fun removeOnCanGoBackRequestedListener()
}

interface OnGoBackRequestedListener {
    /**
     * Occurs when go back is requested.
     */
    fun onGoBackRequested()
}

interface OnCanGoBackRequestedListener {
    /**
     * Occurs when can go back is requested.
     * @return can go back
     */
    fun onCanGoBackRequested(): Boolean
}