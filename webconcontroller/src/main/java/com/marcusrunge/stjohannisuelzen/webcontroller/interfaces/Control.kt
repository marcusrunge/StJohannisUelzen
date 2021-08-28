package com.marcusrunge.stjohannisuelzen.webcontroller.interfaces

interface Control {
    var isWebViewActive:Boolean
    fun goBack()
    fun requestCanGoBack():Boolean
    fun addOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber)
    fun removeOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber)
    fun setOnWebCanGoBackRequestSubscriber(onWebCanGoBackRequestSubscriber: OnWebCanGoBackRequestSubscriber)
    fun removeOnWebCanGoBackRequestSubscriber()
}

interface OnWebGoBackSubscriber {
    fun onWebGoBack()
}

interface OnWebCanGoBackRequestSubscriber {
    fun onWebCanGoBackRequest():Boolean
}