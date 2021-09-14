package com.marcusrunge.stjohannisuelzen.apiconnect.interfaces

import android.content.Context

interface ApiConnectFactory {
    fun create(context: Context?): ApiConnect
}