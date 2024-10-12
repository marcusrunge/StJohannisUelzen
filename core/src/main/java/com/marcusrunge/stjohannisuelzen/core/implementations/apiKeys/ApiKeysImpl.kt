package com.marcusrunge.stjohannisuelzen.core.implementations.apiKeys

import com.marcusrunge.stjohannisuelzen.core.interfaces.ApiKeys

internal class ApiKeysImpl : ApiKeys {
    internal companion object {
        var apiKeys: ApiKeys? = null
        fun create(): ApiKeys = when {
            apiKeys != null -> apiKeys!!
            else -> {
                apiKeys = ApiKeysImpl()
                apiKeys!!
            }
        }
    }

    private lateinit var _googleMaps: String
    private lateinit var _youtube: String
    override var googleMaps: String
        get() = _googleMaps
        set(value) {
            _googleMaps = value
        }
    override var youtube: String
        get() = _youtube
        set(value) {
            _youtube = value
        }

}