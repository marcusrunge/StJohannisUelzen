package com.marcusrunge.stjohannisuelzen.utils

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcusrunge.stjohannisuelzen.R

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun setEndpointUrl(view: WebView, endpointUrl: LiveData<String>) {
        endpointUrl.value?.let {
            view.webViewClient = WebViewClient()
            view.loadUrl(it)
        }
    }

    @BindingAdapter("setAdapter")
    @JvmStatic
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
        val divider = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        divider.setDrawable(
            ContextCompat.getDrawable(
                recyclerView.context,
                R.drawable.shape_transparentlinedivider
            )!!
        )
        recyclerView.addItemDecoration(divider)
    }
}