package com.marcusrunge.stjohannisuelzen.ui.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnCanGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnRequestNavigateToListener
import com.marcusrunge.stjohannisuelzen.databinding.WebFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewFragment : Fragment(), OnGoBackRequestedListener, OnCanGoBackRequestedListener,
    OnRequestNavigateToListener {

    private var _binding: WebFragmentBinding? = null
    private val viewModel by viewModels<WebViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var core: Core
    private lateinit var stJohannisUelzenWebview: WebView
    //private lateinit var stJohannisUelzenWebviewSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        core.back.web.setOnGoBackRequestedListener(this)
        core.back.web.setOnWebCanGoBackRequestedListener(this)
        core.webNavigation.setOnRequestNavigateToListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.web_fragment, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        stJohannisUelzenWebview = binding.stjohannisuelzenWebview
        stJohannisUelzenWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                core.webNavigation.pageFinished()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = request?.url
                if (uri != null && uri.toString().contains(".pdf", ignoreCase = true)) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "application/pdf")
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    try {
                        startActivity(Intent.createChooser(intent, getString(R.string.open_pdf)))
                    } catch (_: Exception) {
                        return false
                    }
                    return true
                }
                return false
            }
        }
        stJohannisUelzenWebview.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val transport = resultMsg?.obj as? WebView.WebViewTransport
                transport?.webView = view
                resultMsg?.sendToTarget()
                return true
            }
        }
        stJohannisUelzenWebview.setDownloadListener { url, _, _, _, _ ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            try {
                startActivity(Intent.createChooser(intent, getString(R.string.open_pdf)))
            } catch (_: Exception) {
            }
        }
        stJohannisUelzenWebview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        stJohannisUelzenWebview.settings.loadsImagesAutomatically = true
        stJohannisUelzenWebview.settings.javaScriptEnabled = true
        stJohannisUelzenWebview.settings.domStorageEnabled = true
        stJohannisUelzenWebview.settings.setSupportMultipleWindows(true)
        stJohannisUelzenWebview.settings.javaScriptCanOpenWindowsAutomatically = true
        stJohannisUelzenWebview.settings.allowFileAccess = true
        stJohannisUelzenWebview.settings.setSupportZoom(true)
        stJohannisUelzenWebview.settings.builtInZoomControls = true
        stJohannisUelzenWebview.settings.displayZoomControls = false
        stJohannisUelzenWebview.settings.loadWithOverviewMode = true
        stJohannisUelzenWebview.settings.useWideViewPort = true
        stJohannisUelzenWebview.requestFocus()
        /*stJohannisUelzenWebviewSwipeRefreshLayout =
            view.findViewById(R.id.stjohannisuelzen_webview_swiperefreshlayout)
        stJohannisUelzenWebviewSwipeRefreshLayout.setOnRefreshListener {
            stJohannisUelzenWebview.reload()
            stJohannisUelzenWebviewSwipeRefreshLayout.isRefreshing = false
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }

    override fun onGoBackRequested() {
        stJohannisUelzenWebview.goBack()
    }

    override fun onCanGoBackRequested(): Boolean {
        return stJohannisUelzenWebview.canGoBack()
    }

    override fun onRequestNavigateTo(url: String) {
        if (::stJohannisUelzenWebview.isInitialized) {
            stJohannisUelzenWebview.loadUrl(url)
        }
    }
}