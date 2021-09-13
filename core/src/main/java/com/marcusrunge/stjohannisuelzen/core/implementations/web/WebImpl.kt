package com.marcusrunge.stjohannisuelzen.core.implementations.web

import com.marcusrunge.stjohannisuelzen.core.bases.BackBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnCanGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.Web

internal class WebImpl(private val backBase: BackBase) : Web {

    internal companion object {
        var web: Web? = null
        fun create(backBase: BackBase): Web = when {
            web != null -> web!!
            else -> {
                web = WebImpl(backBase)
                web!!
            }
        }
    }

    private var onGoBackRequestedListener: OnGoBackRequestedListener? = null
    private var onCanGoBackRequestedListener: OnCanGoBackRequestedListener? = null

    override var isWebViewActive: Boolean = false

    override fun requestGoBack() {
        try {
            onGoBackRequestedListener?.onGoBackRequested()
        } catch (e: Exception) {
        }
    }

    override fun requestCanGoBack(): Boolean {
        return try {
            onCanGoBackRequestedListener?.onCanGoBackRequested()!!
        } catch (e: Exception) {
            false
        }
    }

    override fun setOnGoBackRequestedListener(onGoBackRequestedListener: OnGoBackRequestedListener) {
        this.onGoBackRequestedListener = onGoBackRequestedListener
    }

    override fun removeOnGoBackRequestedListener() {
        onGoBackRequestedListener = null
    }

    override fun setOnWebCanGoBackRequestedListener(onCanGoBackRequestedListener: OnCanGoBackRequestedListener) {
        this.onCanGoBackRequestedListener = onCanGoBackRequestedListener
    }

    override fun removeOnCanGoBackRequestedListener() {
        this.onCanGoBackRequestedListener = null
    }
}