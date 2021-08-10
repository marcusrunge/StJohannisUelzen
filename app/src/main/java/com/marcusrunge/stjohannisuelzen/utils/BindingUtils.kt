package com.marcusrunge.stjohannisuelzen.utils

import android.webkit.WebView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun bindEndpointUrl(view: WebView, endpointUrl: MutableLiveData<String>) {
        endpointUrl.value?.let {
            view.loadUrl(it)
        }
    }
}