package com.marcusrunge.stjohannisuelzen.utils

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marcusrunge.stjohannisuelzen.R
import com.squareup.picasso.Picasso

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun setEndpointUrl(view: WebView, endpointUrl: String) {
        view.webViewClient = WebViewClient()
        view.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        view.settings.loadsImagesAutomatically = true
        view.settings.javaScriptEnabled = true
        view.loadUrl(endpointUrl)
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("onTouchListener")
    @JvmStatic
    fun setOnTouchListener(view: WebView, onTouchListener: View.OnTouchListener) {
        view.setOnTouchListener(onTouchListener)
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        Picasso.get().load(imageUrl).into(view)
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

    @BindingAdapter("setOnRefreshListener")
    @JvmStatic
    fun bindOnRefreshListener(
        view: SwipeRefreshLayout,
        listener: SwipeRefreshLayout.OnRefreshListener?
    ) {
        view.setOnRefreshListener(listener)
    }

    @BindingAdapter("setIsRefreshing")
    @JvmStatic
    fun bindIsRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }
}