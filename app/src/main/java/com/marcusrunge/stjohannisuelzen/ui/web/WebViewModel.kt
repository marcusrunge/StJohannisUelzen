package com.marcusrunge.stjohannisuelzen.ui.web

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcusrunge.stjohannisuelzen.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    @get:Bindable
    val endpointUrl
        get() = context.getString(R.string.url_stjohannis_uelzen)
}