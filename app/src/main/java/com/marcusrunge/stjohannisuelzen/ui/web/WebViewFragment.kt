package com.marcusrunge.stjohannisuelzen.ui.web

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnCanGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnGoBackRequestedListener
import com.marcusrunge.stjohannisuelzen.databinding.WebFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewFragment : Fragment(), OnGoBackRequestedListener, OnCanGoBackRequestedListener {

    private var _binding: WebFragmentBinding? = null
    private val viewModel by viewModels<WebViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var core: Core
    private lateinit var stJohannisUelzenWebview: WebView
    private lateinit var stJohannisUelzenWebviewSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        core.back.web.isWebViewActive = true
        core.back.web.setOnGoBackRequestedListener(this)
        core.back.web.setOnWebCanGoBackRequestedListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.web_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        stJohannisUelzenWebview = view.findViewById(R.id.stjohannisuelzen_webview)
        stJohannisUelzenWebviewSwipeRefreshLayout =
            view.findViewById(R.id.stjohannisuelzen_webview_swiperefreshlayout)
        stJohannisUelzenWebviewSwipeRefreshLayout.setOnRefreshListener {
            stJohannisUelzenWebview.reload()
            stJohannisUelzenWebviewSwipeRefreshLayout.isRefreshing = false
        }
        setTheme()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        core.back.web.isWebViewActive = false
        core.back.web.removeOnGoBackRequestedListener()
        core.back.web.removeOnCanGoBackRequestedListener()
    }

    override fun onGoBackRequested() {
        stJohannisUelzenWebview.goBack()
    }

    override fun onCanGoBackRequested(): Boolean {
        return stJohannisUelzenWebview.canGoBack()
    }

    private fun setTheme() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setForceDark(stJohannisUelzenWebview.settings, FORCE_DARK_ON)
                }
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setForceDark(
                        stJohannisUelzenWebview.settings,
                        FORCE_DARK_OFF
                    )
                }
                else -> {
                    //
                }
            }
        }
    }
}