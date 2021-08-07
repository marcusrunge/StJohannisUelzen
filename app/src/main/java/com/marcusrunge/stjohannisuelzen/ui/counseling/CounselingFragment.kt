package com.marcusrunge.stjohannisuelzen.ui.counseling

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marcusrunge.stjohannisuelzen.R

class CounselingFragment : Fragment() {

    companion object {
        fun newInstance() = CounselingFragment()
    }

    private lateinit var viewModel: CounselingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.counseling_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CounselingViewModel::class.java)
        // TODO: Use the ViewModel
    }
}