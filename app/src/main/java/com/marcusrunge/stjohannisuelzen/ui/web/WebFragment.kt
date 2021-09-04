package com.marcusrunge.stjohannisuelzen.ui.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.databinding.WebFragmentBinding
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebCanGoBackRequestSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebGoBackSubscriber
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebFragment : Fragment(), OnWebGoBackSubscriber, OnWebCanGoBackRequestSubscriber {

    private var _binding: WebFragmentBinding? = null
    private val viewModel by viewModels<WebViewModel>()
    private val binding get() = _binding!!
    @Inject
    lateinit var core: Core
    private lateinit var stjohannisuelzen_webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        core.webController.control.isWebViewActive = true
        core.webController.control.addOnWebGoBackSubscriber(this)
        core.webController.control.setOnWebCanGoBackRequestSubscriber(this)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        core.webController.control.isWebViewActive = false
        core.webController.control.removeOnWebGoBackSubscriber(this)
        core.webController.control.removeOnWebCanGoBackRequestSubscriber()
    }

    override fun onWebGoBack() {
        stjohannisuelzen_webview.goBack()
    }

    override fun onWebCanGoBackRequest(): Boolean {
        return stjohannisuelzen_webview.canGoBack()
    }
}