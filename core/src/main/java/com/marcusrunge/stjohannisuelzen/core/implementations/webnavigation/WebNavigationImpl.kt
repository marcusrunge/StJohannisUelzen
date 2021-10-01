package com.marcusrunge.stjohannisuelzen.core.implementations.webnavigation

import com.marcusrunge.stjohannisuelzen.core.interfaces.OnRequestNavigateToListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.WebNavigation

internal class WebNavigationImpl : WebNavigation {

    internal companion object {
        var webNavigation: WebNavigation? = null
        fun create(): WebNavigation = when {
            webNavigation != null -> webNavigation!!
            else -> {
                webNavigation = WebNavigationImpl()
                webNavigation!!
            }
        }
    }

    private var onRequestNavigateToListener: OnRequestNavigateToListener? = null
    override fun requestNavigateTo(url: String) {
        onRequestNavigateToListener?.onRequestNavigateTo(url)
    }

    override fun setOnRequestNavigateToListener(onRequestNavigateToListener: OnRequestNavigateToListener) {
        this.onRequestNavigateToListener = onRequestNavigateToListener
    }

    override fun removeOnRequestNavigateToListener() {
        this.onRequestNavigateToListener = null
    }
}