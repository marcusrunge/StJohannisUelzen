package com.marcusrunge.stjohannisuelzen.ui.calendar

import android.content.Context
import android.os.Message
import androidx.databinding.Bindable
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModelBase() {

    @get:Bindable
    val endpointUrl
        get() = context.getString(R.string.url_churchservices)

    override fun updateView(inputMessage: Message) {
        TODO("Not yet implemented")
    }
}