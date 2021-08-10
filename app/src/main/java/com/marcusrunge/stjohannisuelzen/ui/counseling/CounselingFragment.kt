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

    private lateinit var viewDataBinding: CounselingFragmentBinding
    private val viewModel by viewModels<CounselingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.counseling_fragment, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }
}