package com.marcusrunge.stjohannisuelzen.ui.media

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.MediaFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] for displaying media content, primarily from YouTube.
 *
 * This fragment uses a [WebView] to display a curated YouTube playlist. It follows the MVVM
 * pattern, with [MediaViewModel] providing the data and state. Data binding is used to
 * connect the layout with the ViewModel, and the WebView is configured for optimal
 * video playback.
 */
@AndroidEntryPoint
class MediaFragment : Fragment() {
    private var _binding: MediaFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MediaViewModel>()

    // region Lifecycle Methods

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * This is where the layout is inflated and data binding is initialized.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.media_fragment, container, false)
        return binding.root
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored.
     *
     * This is where the ViewModel and LifecycleOwner are set for data binding, and the WebView is configured.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        setupWebView()
    }

    /**
     * Called when the view previously created by [onCreateView] has been detached from the fragment.
     *
     * It's crucial to clear the reference to the binding object here to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks. The binding holds a reference to the
        // view hierarchy, which should be garbage collected when the view is destroyed.
        _binding = null
    }

    // endregion

    // region Private Helpers

    /**
     * Configures the WebView settings for optimal media playback.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.youtubeWebview.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    // endregion
}
