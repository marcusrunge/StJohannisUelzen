package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData

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