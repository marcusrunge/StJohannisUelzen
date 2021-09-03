package com.marcusrunge.stjohannisuelzen.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.interfaces.OnCanGoBackChangedListener
import com.marcusrunge.stjohannisuelzen.webcontroller.enums.Direction
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebCanGoBackRequestSubscriber
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.OnWebGoBackSubscriber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(val core: Core) :
    ViewModel(), OnWebGoBackSubscriber, OnWebCanGoBackRequestSubscriber,
    OnCanGoBackChangedListener {

    private val _endpointUrl = MutableLiveData(core.webController.sources.endpointUrl!!)
    private val _canGoBack = MutableLiveData<(Boolean) -> Unit>()
    private val _goDirection = MutableLiveData<(Boolean) -> Direction>()

    val endpointUrl: LiveData<String> = _endpointUrl

    init {
        core.webController.control.addOnWebGoBackSubscriber(this)
        core.webController.control.setOnWebCanGoBackRequestSubscriber(this)
        core.webController.control.isWebViewActive = true
    }

    override fun onCleared() {
        core.webController.control.removeOnWebGoBackSubscriber(this)
        core.webController.control.removeOnWebCanGoBackRequestSubscriber()
        core.webController.control.isWebViewActive = false
        super.onCleared()
    }

    override fun onWebGoBack() {
        TODO("Not yet implemented")
    }

    override fun onWebCanGoBackRequest(): Boolean {
        TODO("Not yet implemented")
    }

    override fun OnCanGoBack(canGoBack: Boolean) {
        TODO("Not yet implemented")
    }
}