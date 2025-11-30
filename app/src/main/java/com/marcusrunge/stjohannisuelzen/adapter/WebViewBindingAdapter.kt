package com.marcusrunge.stjohannisuelzen.adapter

import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter("data", "origin", "mimeType", "encoding")
fun loadDataWithOrigin(
    webView: WebView,
    data: String?,
    origin: String?,
    mimeType: String?,
    encoding: String?
) {
    if (data == null || origin == null || mimeType == null || encoding == null) {
        return
    }
    webView.loadDataWithBaseURL(origin, data, mimeType, encoding, null)
}
