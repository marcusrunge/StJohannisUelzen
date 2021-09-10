package com.marcusrunge.stjohannisuelzen.core.interfaces

interface App {
    fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener)
    fun removeOnBackPressedListener(onBackPressedListener: OnBackPressedListener)
    fun onBackPressed(callback: (() -> Unit)?)
}

interface OnBackPressedListener {
    fun onBackPressed():Boolean
}