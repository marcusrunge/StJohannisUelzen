package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(private val core: Core) :
    ViewModel() {

    private val _endpointUrl = MutableLiveData(core.webController.sources.endpointUrl!!)

    val endpointUrl: LiveData<String> = _endpointUrl
}