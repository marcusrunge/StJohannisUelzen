package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(val webController: WebController) : ViewModel() {

    private val _endpointUrl = MutableLiveData(webController.sources.endpointUrl!!)

    val endpointUrl: LiveData<String> = _endpointUrl
}