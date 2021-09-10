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
class Fragment : Fragment(), OnGoBackRequestedListener, OnCanGoBackRequestedListener {

    private var _binding: WebFragmentBinding? = null
    private val viewModel by viewModels<WebViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var core: Core
    private lateinit var stjohannisuelzen_webview: WebView

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
        stjohannisuelzen_webview = view.findViewById(R.id.stjohannisuelzen_webview)
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
        stjohannisuelzen_webview.goBack()
    }

    override fun onCanGoBackRequested(): Boolean {
        return stjohannisuelzen_webview.canGoBack()
    }

    private fun setTheme() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setForceDark(stjohannisuelzen_webview.settings, FORCE_DARK_ON)
                }
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setForceDark(
                        stjohannisuelzen_webview.settings,
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