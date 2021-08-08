package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(val webController: WebController): ViewModel() {
    // TODO: Implement the ViewModel
}