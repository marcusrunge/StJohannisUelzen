package com.marcusrunge.stjohannisuelzen.ui.web

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(@ApplicationContext context: Context) :
    ViewModel() {

    private val _endpointUrl = MutableLiveData(context.getString(R.string.url_stjohannis_uelzen))

    val endpointUrl: LiveData<String> = _endpointUrl
}