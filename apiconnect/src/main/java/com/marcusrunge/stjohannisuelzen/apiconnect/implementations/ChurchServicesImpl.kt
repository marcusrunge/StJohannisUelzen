package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import com.marcusrunge.stjohannisuelzen.apiconnect.bases.ApiConnectBase
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ChurchServices

internal class ChurchServicesImpl(private val apiConnectBase: ApiConnectBase) : ChurchServices {
    companion object {
        private var churchServices: ChurchServices? = null
        fun create(apiConnectBase: ApiConnectBase): ChurchServices = when {
            churchServices != null -> churchServices!!
            else -> {
                churchServices = ChurchServicesImpl(apiConnectBase)
                churchServices!!
            }
        }
    }
}