package com.marcusrunge.stjohannisuelzen.ui.counseling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.CounselingFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CounselingFragment : Fragment() {

    private var _binding: CounselingFragmentBinding? = null
    private val viewModel by viewModels<CounselingViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.counseling_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}