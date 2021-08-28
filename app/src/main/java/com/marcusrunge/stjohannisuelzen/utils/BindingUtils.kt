package com.marcusrunge.stjohannisuelzen.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.marcusrunge.stjohannisuelzen.webcontroller.enums.Direction

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun setEndpointUrl(view: WebView, endpointUrl: LiveData<String>) {
        endpointUrl.value?.let {
            view.webViewClient = WebViewClient()
            view.loadUrl(it)
        }
    }
}