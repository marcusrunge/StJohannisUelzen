package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Swipe {
    fun onSwipe(swipe:com.marcusrunge.stjohannisuelzen.core.enums.Swipe, value:Int)
    fun setOnSwipeListener(onSwipeListener: OnSwipeListener)
    fun removeOnSwipeListener(onSwipeListener: OnSwipeListener)
}

interface OnSwipeListener {
    fun onSwipe(swipe:com.marcusrunge.stjohannisuelzen.core.enums.Swipe, value:Int)
}