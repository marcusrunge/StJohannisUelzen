package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(private val webController: WebController, val core: Core) :
    ViewModel(),
    OnBackSubscriber {

    private val _endpointUrl = MutableLiveData(webController.sources.endpointUrl!!)
    val endpointUrl: LiveData<String> = _endpointUrl


    init {
        core.back.subscriber.add(this)
    }

    override fun onBack() {
        webController.control.goBack()
    }

    override fun onCleared() {
        core.back.subscriber.remove(this)
        super.onCleared()
    }
}