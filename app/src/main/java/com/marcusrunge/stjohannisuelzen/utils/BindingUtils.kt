package com.marcusrunge.stjohannisuelzen.utils

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.marcusrunge.stjohannisuelzen.R
import com.squareup.picasso.Picasso

object BindingUtils {
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun setEndpointUrl(view: WebView, endpointUrl: String) {
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

    @BindingAdapter("tapUpPoint")
    @JvmStatic
    fun setTapUpPoint(view: WebView, tapUpPoint: Point?) {
        if (tapUpPoint != null) {
            val motionEventDown = MotionEvent.obtain(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis() + 100,
                MotionEvent.ACTION_DOWN,
                tapUpPoint.x.toFloat(),
                tapUpPoint.y.toFloat(),
                0
            )
            val motionEventUp = MotionEvent.obtain(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis() + 100,
                MotionEvent.ACTION_UP,
                tapUpPoint.x.toFloat(),
                tapUpPoint.y.toFloat(),
                0
            )
            view.onTouchEvent(motionEventDown)
            view.onTouchEvent(motionEventUp)
        }
    }

    @BindingAdapter("velocityY")
    @JvmStatic
    fun setVelocityY(view: WebView, velocityY: Int?) {
        if (velocityY != null) {
            view.flingScroll(0, velocityY)
        }
    }

    @BindingAdapter("scrollY")
    @JvmStatic
    fun setScrollY(view: WebView, scrollY: Int?) {
        if (scrollY != null) {
            view.scrollBy(0, scrollY)
        }
    }

    @BindingAdapter("onMapReadyCallback")
    @JvmStatic
    fun setOnMapReadyCallback(
        view: androidx.fragment.app.FragmentContainerView,
        callback: OnMapReadyCallback?
    ) {
        if (callback != null) {
            view.getFragment<SupportMapFragment>().getMapAsync(callback)
        }
    }

    /*@BindingAdapter(value = ["bind:baseUrl", "bind:data", "bind:mimeType", "bind:encoding"], requireAll = true)
    @JvmStatic
    fun bindDataWithBaseURL(view: WebView,baseUrl:String, data: String, mimeType:String, encoding:String) {
        view.loadDataWithBaseURL(baseUrl, data,mimeType,encoding, null)
    }*/

    @BindingAdapter(value = ["data", "mimeType", "encoding"], requireAll = true)
    @JvmStatic
    fun bindData(view: WebView, data: String, mimeType: String, encoding: String) {
        view.loadData(data, mimeType, encoding)
    }
}