package com.marcusrunge.stjohannisuelzen.apiconnect.interfaces

import android.content.Context

interface ApiConnectFactory {
    /**
     * Creates ApiConnect
     * @param context the context.
     */
    fun create(context: Context?): ApiConnect
}