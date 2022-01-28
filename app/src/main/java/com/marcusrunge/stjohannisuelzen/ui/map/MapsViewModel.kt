package com.marcusrunge.stjohannisuelzen.ui.map

import android.content.Context
import android.os.Message
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MapsViewModel @Inject constructor(    @ApplicationContext val context: Context
) : ViewModelBase() {
    override fun updateView(inputMessage: Message) {
        TODO("Not yet implemented")
    }
}