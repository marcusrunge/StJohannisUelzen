package com.marcusrunge.stjohannisuelzen.apiconnect.implementations

import android.content.Context
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnectFactory

class ApiConnectFactoryImpl {
    companion object : ApiConnectFactory {
        private var apiConnect: ApiConnect? = null
        override fun create(context: Context?): ApiConnect = when {
            apiConnect != null -> apiConnect!!
            else -> {
                apiConnect = ApiConnectImpl(context)
                apiConnect!!
            }
        }
    }
}