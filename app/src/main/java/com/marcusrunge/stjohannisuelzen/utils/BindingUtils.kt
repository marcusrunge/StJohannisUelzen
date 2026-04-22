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
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.marcusrunge.stjohannisuelzen.R
import com.squareup.picasso.Picasso

/**
 * A utility object containing custom [BindingAdapter]s for use with Data Binding.
 * These adapters provide custom logic for binding data to views in XML layouts.
 */
object BindingUtils {

    //region WebView Adapters

    /**
     * Binds a URL to a [WebView], causing it to load the specified web page.
     *
     * Example usage in XML:
     * ```xml
     * <WebView
     *     ...
     *     app:endpointUrl="@{viewModel.url}"
     *     ... />
     * ```
     *
     * @param view The [WebView] instance.
     * @param endpointUrl The URL to load in the [WebView].
     */
    @BindingAdapter("endpointUrl")
    @JvmStatic
    fun setEndpointUrl(view: WebView, endpointUrl: String?) {
        endpointUrl?.let { view.loadUrl(it) }
    }

    /**
     * Binds a custom [View.OnTouchListener] to a [WebView].
     *
     * @param view The [WebView] instance.
     * @param onTouchListener The listener to attach to the [WebView].
     */
    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("onTouchListener")
    @JvmStatic
    fun setOnTouchListener(view: WebView, onTouchListener: View.OnTouchListener) {
        view.setOnTouchListener(onTouchListener)
    }

    /**
     * Binds a [Point] to a [WebView] to simulate a tap event at the given coordinates.
     * This is useful for programmatically triggering click events on the web content.
     *
     * @param view The [WebView] instance.
     * @param tapUpPoint The [Point] coordinates for the tap simulation. If null, no action is taken.
     */
    @BindingAdapter("tapUpPoint")
    @JvmStatic
    fun setTapUpPoint(view: WebView, tapUpPoint: Point?) {
        if (tapUpPoint != null) {
            val downTime = SystemClock.uptimeMillis()
            val eventTime = downTime + 100
            val motionEventDown = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_DOWN,
                tapUpPoint.x.toFloat(),
                tapUpPoint.y.toFloat(),
                0
            )
            val motionEventUp = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                tapUpPoint.x.toFloat(),
                tapUpPoint.y.toFloat(),
                0
            )
            view.onTouchEvent(motionEventDown)
            view.onTouchEvent(motionEventUp)
            motionEventDown.recycle()
            motionEventUp.recycle()
        }
    }

    /**
     * Binds a Y-velocity value to a [WebView] to initiate a fling scroll.
     *
     * @param view The [WebView] instance.
     * @param velocityY The initial velocity for the fling scroll on the Y-axis. If null, no action is taken.
     */
    @BindingAdapter("velocityY")
    @JvmStatic
    fun setVelocityY(view: WebView, velocityY: Int?) {
        if (velocityY != null) {
            view.flingScroll(0, velocityY)
        }
    }

    /**
     * Binds a Y-scroll value to a [WebView] to scroll it by a certain amount.
     *
     * @param view The [WebView] instance.
     * @param scrollY The amount to scroll on the Y-axis. If null, no action is taken.
     */
    @BindingAdapter("scrollY")
    @JvmStatic
    fun setScrollY(view: WebView, scrollY: Int?) {
        if (scrollY != null) {
            view.scrollBy(0, scrollY)
        }
    }

    /**
     * Binds HTML data to a [WebView].
     *
     * Example usage in XML:
     * ```xml
     * <WebView
     *     ...
     *     app:data="@{viewModel.htmlContent}"
     *     app:mimeType="@{"text/html"}"
     *     app:encoding="@{"UTF-8"}"
     *     ... />
     * ```
     *
     * @param view The [WebView] instance.
     * @param data The HTML data string.
     * @param mimeType The MIME type of the data, e.g., "text/html".
     * @param encoding The encoding of the data, e.g., "UTF-8".
     */
    @BindingAdapter(value = ["data", "mimeType", "encoding"], requireAll = true)
    @JvmStatic
    fun bindData(view: WebView, data: String?, mimeType: String, encoding: String) {
        data?.let { view.loadData(it, mimeType, encoding) }
    }

    //endregion

    //region ImageView Adapters

    /**
     * Binds an image URL to an [ImageView], loading the image using Picasso.
     *
     * Example usage in XML:
     * ```xml
     * <ImageView
     *     ...
     *     app:imageUrl="@{viewModel.imageUrl}"
     *     ... />
     * ```
     *
     * @param view The [ImageView] instance.
     * @param imageUrl The URL of the image to load. If null or empty, no image is loaded.
     */
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(view)
        }
    }

    //endregion

    //region RecyclerView Adapters

    /**
     * Binds an adapter to a [RecyclerView]. It also sets a [LinearLayoutManager] and adds
     * a vertical [DividerItemDecoration] with a custom transparent drawable.
     *
     * @param recyclerView The [RecyclerView] instance.
     * @param adapter The [RecyclerView.Adapter] to be set to the [RecyclerView].
     */
    @BindingAdapter("setAdapter")
    @JvmStatic
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
        val divider = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
            recyclerView.context,
            R.drawable.shape_transparentlinedivider
        )?.let {
            divider.setDrawable(it)
            recyclerView.addItemDecoration(divider)
        }
    }

    //endregion

    //region SwipeRefreshLayout Adapters

    /**
     * Binds an [SwipeRefreshLayout.OnRefreshListener] to a [SwipeRefreshLayout].
     *
     * @param view The [SwipeRefreshLayout] instance.
     * @param listener The listener to be invoked when a refresh is triggered.
     */
    @BindingAdapter("setOnRefreshListener")
    @JvmStatic
    fun bindOnRefreshListener(
        view: SwipeRefreshLayout,
        listener: SwipeRefreshLayout.OnRefreshListener?
    ) {
        view.setOnRefreshListener(listener)
    }

    /**
     * Binds the refreshing state to a [SwipeRefreshLayout].
     *
     * Example usage in XML:
     * ```xml
     * <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
     *     ...
     *     app:setIsRefreshing="@{viewModel.isRefreshing}"
     *     ... >
     * ```
     *
     * @param view The [SwipeRefreshLayout] instance.
     * @param isRefreshing A boolean indicating whether the refresh indicator should be displayed.
     */
    @BindingAdapter("setIsRefreshing")
    @JvmStatic
    fun bindIsRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }

    //endregion

    //region Map Adapters

    /**
     * Binds an [OnMapReadyCallback] to a [SupportMapFragment] hosted within a [FragmentContainerView].
     * Note: This adapter assumes the fragment inside the container is a [SupportMapFragment].
     *
     * @param view The [FragmentContainerView] that hosts the [SupportMapFragment].
     * @param callback The callback to be invoked when the map is ready to be used.
     */
    @BindingAdapter("onMapReadyCallback")
    @JvmStatic
    fun setOnMapReadyCallback(
        view: FragmentContainerView,
        callback: OnMapReadyCallback?
    ) {
        if (callback != null) {
            val fragment = view.getFragment<SupportMapFragment>()
            fragment.getMapAsync(callback)
        }
    }

    //endregion
}
