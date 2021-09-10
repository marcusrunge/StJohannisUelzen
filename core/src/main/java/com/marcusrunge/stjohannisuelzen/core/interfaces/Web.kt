package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Web {
    var isWebViewActive: Boolean
    fun requestGoBack()
    fun requestCanGoBack(): Boolean
    fun setOnGoBackRequestedListener(onGoBackRequestedListener: OnGoBackRequestedListener)
    fun removeOnGoBackRequestedListener()
    fun setOnWebCanGoBackRequestedListener(onCanGoBackRequestedListener: OnCanGoBackRequestedListener)
    fun removeOnCanGoBackRequestedListener()
}

interface OnGoBackRequestedListener {
    fun onGoBackRequested()
}

interface OnCanGoBackRequestedListener {
    fun onCanGoBackRequested(): Boolean
}