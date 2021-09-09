package com.marcusrunge.stjohannisuelzen.implementations

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.marcusrunge.stjohannisuelzen.MainActivity
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.interfaces.Navigation

class NavigationImpl(val mainActivity: MainActivity) : Navigation {

    internal companion object {
        var navigation: Navigation? = null
        fun create(context: Context): Navigation = when {
            navigation != null -> navigation!!
            else -> {
                navigation = NavigationImpl(context as MainActivity)
                navigation!!
            }
        }
    }

    private var isActivated = false
    private lateinit var _navController: NavController
    override val navController: NavController
        get() = _navController

    override fun activate() {
        _navController = mainActivity.findNavController(R.id.nav_host_fragment_main_activity)
        isActivated = true
    }

    override fun navigateBack() {
        if (isActivated) {
            navController.popBackStack()
        }
    }
}