package com.marcusrunge.stjohannisuelzen.webcontroller.interfaces

interface Control {
    var canGoBack:Boolean
    fun goBack()
    fun addOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber)
    fun removeOnWebGoBackSubscriber(onWebGoBackSubscriber: OnWebGoBackSubscriber)
}

interface OnWebGoBackSubscriber {
    fun onWebGoBack()
}