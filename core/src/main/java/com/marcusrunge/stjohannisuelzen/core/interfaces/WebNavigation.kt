package com.marcusrunge.stjohannisuelzen.core.interfaces

interface WebNavigation {
    fun requestNavigateTo(url: String)
    fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener)
    fun removeOnRequestNavigateToListener()
}

interface OnRequestNavigateToListener {
    fun onRequestNavigateTo(url: String)
}