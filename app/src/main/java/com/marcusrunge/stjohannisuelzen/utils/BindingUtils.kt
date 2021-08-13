package com.marcusrunge.stjohannisuelzen.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun bindEndpointUrl(view: WebView, endpointUrl: LiveData<String>) {
        endpointUrl.value?.let {
            view.webViewClient = WebViewClient()
            view.loadUrl(it)
        }
    }
}