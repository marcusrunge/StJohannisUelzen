/**
 * This file contains custom [BindingAdapter]s for use with [WebView].
 *
 * Binding adapters are a powerful feature of the Data Binding Library that allow you to customize
 * how data is set on views. These adapters are particularly useful for encapsulating complex
 * view logic and making your XML layouts cleaner and more declarative.
 */
package com.marcusrunge.stjohannisuelzen.adapter

import android.webkit.WebView
import androidx.databinding.BindingAdapter

/**
 * A custom [BindingAdapter] for loading HTML data into a [WebView] with a specified base URL.
 *
 * This adapter allows you to load HTML content directly into a WebView from your layout XML.
 * It ensures that all required parameters (`data`, `origin`, `mimeType`, `encoding`) are non-null
 * before attempting to load the data, preventing potential `NullPointerException`s.
 *
 *
 * Usage in XML:
 * ```xml
 * <WebView
 *     ...
 *     app:data="@{viewModel.htmlContent}"
 *     app:origin="@{viewModel.baseUrl}"
 *     app:mimeType="@{'text/html'}"
 *     app:encoding="@{'UTF-8'}"
 *     ... />
 * ```
 *
 * @param webView The [WebView] instance this adapter is applied to.
 * @param data The HTML data string to load.
 * @param origin The base URL to use as the origin of the content. This is important for resolving
 *               relative paths for resources like images, CSS, or JavaScript.
 * @param mimeType The MIME type of the data (e.g., "text/html").
 * @param encoding The encoding of the data (e.g., "UTF-8").
 */
@BindingAdapter("data", "origin", "mimeType", "encoding")
fun loadDataWithOrigin(
    webView: WebView,
    data: String?,
    origin: String?,
    mimeType: String?,
    encoding: String?
) {
    // Guard against null values for any of the required parameters. If any are null,
    // we cannot proceed with loading the data.
    if (data == null || origin == null || mimeType == null || encoding == null) {
        return
    }

    // Load the HTML data into the WebView. The `loadDataWithBaseURL` method is used
    // so that relative URLs within the HTML content (e.g., for images) can be resolved
    // correctly against the provided `origin`. The history URL is set to null as we don't
    // need to add this to the back stack.
    webView.loadDataWithBaseURL(origin, data, mimeType, encoding, null)
}
