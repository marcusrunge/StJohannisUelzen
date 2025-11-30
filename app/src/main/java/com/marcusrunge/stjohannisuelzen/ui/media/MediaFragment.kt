package com.marcusrunge.stjohannisuelzen.ui.media

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.MediaFragmentBinding
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MediaFragment : Fragment() {
    private var _binding: MediaFragmentBinding? = null
    private val viewModel by viewModels<MediaViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var notification: Notification

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.media_fragment, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        val youtubeWebview = view.findViewById<WebView>(R.id.youtube_webview)
        youtubeWebview.settings.javaScriptEnabled = true
        youtubeWebview.settings.loadWithOverviewMode = true
        youtubeWebview.settings.useWideViewPort = true
        youtubeWebview.settings.domStorageEnabled = true
        youtubeWebview.settings.mediaPlaybackRequiresUserGesture = false
        youtubeWebview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}