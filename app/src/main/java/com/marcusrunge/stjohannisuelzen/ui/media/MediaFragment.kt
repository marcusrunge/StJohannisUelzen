package com.marcusrunge.stjohannisuelzen.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.marcusrunge.stjohannisuelzen.BuildConfig
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
    private var youTubePlayer: YouTubePlayer? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        val youTubeFragment =
            childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment
        youTubeFragment.initialize(
            BuildConfig.YOUTUBE_DATA_API_KEY,
            object : OnInitializedListener {
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    youTubePlayer = p1
                    youTubePlayer?.cueVideo(viewModel.liveVideoId.value)
                    viewModel.liveVideoId.observe(viewLifecycleOwner) {
                        try {
                            youTubePlayer?.cueVideo(it)
                        } catch (e: IllegalStateException) {
                            notification.toast.showLong(e.message)
                        }
                    }
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    notification.toast.showLong(p1?.name)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
        youTubePlayer = null
    }
}