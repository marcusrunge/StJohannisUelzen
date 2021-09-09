package com.marcusrunge.stjohannisuelzen.interfaces

import androidx.navigation.NavController

interface Navigation {
    val navController: NavController
    fun activate()
    fun navigateBack()
}