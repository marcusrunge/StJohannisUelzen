package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Scroll {
    fun onScroll(scroll: com.marcusrunge.stjohannisuelzen.core.enums.Scroll, value: Int)
    fun addOnScrollListener(onScrollListener: OnScrollListener)
    fun removeOnScrollListener(onScrollListener: OnScrollListener)
}

interface OnScrollListener {
    fun onScroll(scroll: com.marcusrunge.stjohannisuelzen.core.enums.Scroll, value: Int)
}