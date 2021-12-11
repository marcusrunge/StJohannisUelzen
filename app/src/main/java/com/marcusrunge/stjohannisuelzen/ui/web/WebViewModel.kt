package com.marcusrunge.stjohannisuelzen.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.os.Message
import android.view.MotionEvent
import android.view.View
import androidx.databinding.Bindable
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class WebViewModel @Inject constructor(@ApplicationContext private val context: Context, val core: Core) :
    ViewModelBase(), View.OnTouchListener {

    @get:Bindable
    val endpointUrl
        get() = context.getString(R.string.url_stjohannis_uelzen)

    override fun updateView(inputMessage: Message) {
        //TODO("Not yet implemented")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when (p1?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
        }
        return true
    }
}