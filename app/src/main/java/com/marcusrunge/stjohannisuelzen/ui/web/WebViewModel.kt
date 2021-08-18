package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnBackSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(private val webController: WebController, val core: Core) : ViewModel(),
    OnBackSubscriber {

    private val _endpointUrl = MutableLiveData(webController.sources.endpointUrl!!)

    val endpointUrl: LiveData<String> = _endpointUrl
    lateinit var goBack:LiveData<(()-> Unit)?>
    var canGoBack:LiveData<Boolean> = MutableLiveData()
        set(value) { webController.control.canGoBack = value.value!! }

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