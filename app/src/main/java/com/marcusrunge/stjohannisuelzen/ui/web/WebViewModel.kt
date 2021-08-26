package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebGoBackSubscriber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(val core: Core) :
    ViewModel(), OnWebGoBackSubscriber {

    private val _endpointUrl = MutableLiveData(core.webController.sources.endpointUrl!!)
    private val _canGoBack = MutableLiveData<(Boolean)->Unit>()
    val endpointUrl: LiveData<String> = _endpointUrl
    val canGoBack: LiveData<(Boolean)->Unit> = _canGoBack

    init {
        core.webController.control.addOnWebGoBackSubscriber(this)
        core.webController.control.isWebViewActive = true
    }

    override fun onCleared() {
        core.webController.control.removeOnWebGoBackSubscriber(this)
        core.webController.control.isWebViewActive = false
        super.onCleared()
    }

    override fun onWebGoBack() {
        _canGoBack.value = {
            core.webController.control.canGoBack = it
        }
        TODO("Not yet implemented")
    }
}