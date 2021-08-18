package com.marcusrunge.stjohannisuelzen.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
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

    @InverseBindingAdapter(attribute = "goBack")
    @JvmStatic fun getGoBack(view: WebView): ()->Unit {
        view
        return { view.goBack() }
    }

    @InverseBindingAdapter(attribute = "canGoBack", event = "canGoBackAttrChanged")
    @JvmStatic fun getCanGoBack(view: WebView): Boolean {
        return view.canGoBack()
    }
}