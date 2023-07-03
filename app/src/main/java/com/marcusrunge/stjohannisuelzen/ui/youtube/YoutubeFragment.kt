package com.marcusrunge.stjohannisuelzen.ui.youtube

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.CalendarFragmentBinding
import com.marcusrunge.stjohannisuelzen.ui.calendar.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoutubeFragment : Fragment() {

    companion object {
        fun newInstance() = YoutubeFragment()
    }

    private var _binding: YoutubeFragmentBinding? = null
    private val viewModel by viewModels<YoutubeViewModel>()
    private val binding get() = _binding!!
    private lateinit var youtubeWebview: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_youtube, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        youtubeWebview = view.findViewById(R.id.youtube_webview)
    }
}