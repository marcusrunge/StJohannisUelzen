package com.marcusrunge.stjohannisuelzen.core.interfaces

interface WebNavigation {
    fun requestNavigateTo(url: String)
    fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener)
    fun removeOnRequestNavigateToListener()
    fun setOnPageFinishedListener(onPageFinishedListener: OnPageFinishedListener)
    fun removeOnPageFinishedListener()
    fun pageFinished()
}

interface OnRequestNavigateToListener {
    fun onRequestNavigateTo(url: String)
}

interface OnPageFinishedListener {
    fun onPageFinished()
}