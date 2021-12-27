package com.marcusrunge.stjohannisuelzen.core.interfaces

interface WebNavigation {
    /**
     * Requests web navigation.
     * @param url the website url
     */
    fun requestNavigateTo(url: String)

    /**
     * Sets an OnRequestNavigateToListener,
     * @param onRequestNavigateToListener the listener.
     */
    fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener)

    /**
     * Removes the listener.
     */
    fun removeOnRequestNavigateToListener()

    /**
     * Sets an OnPageFinishedListener,
     * @param onPageFinishedListener the listener.
     */
    fun setOnPageFinishedListener(onPageFinishedListener: OnPageFinishedListener)

    /**
     * Removes the listener.
     */
    fun removeOnPageFinishedListener()

    /**
     * Executes page finished.
     */
    fun pageFinished()
}

interface OnRequestNavigateToListener {
    /**
     * Occurs when web navigation is requested.
     * @param url the website url.
     */
    fun onRequestNavigateTo(url: String)
}

interface OnPageFinishedListener {
    /**
     * Occurs when web page loading has finished.
     */
    fun onPageFinished()
}