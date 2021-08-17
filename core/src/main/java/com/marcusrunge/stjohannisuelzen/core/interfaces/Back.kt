package com.marcusrunge.stjohannisuelzen.core.interfaces

interface Back {
    val subscriber: Subscriber
    val publisher: Publisher
}

interface Subscriber {
    fun add(onBackSubscriber: OnBackSubscriber)
    fun remove(onBackSubscriber: OnBackSubscriber)
}

interface Publisher {
    fun onBack(callback: (() -> Unit)?)
}

interface OnBackSubscriber {
    fun onBack()
}