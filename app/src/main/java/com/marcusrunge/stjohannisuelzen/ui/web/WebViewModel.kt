package com.marcusrunge.stjohannisuelzen.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.os.Message
import android.view.MotionEvent
import android.view.View
import androidx.databinding.Bindable
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModelBase(), View.OnTouchListener {

    private val HORIZONTAL_MIN_DISTANCE = 30
    private val VERTICAL_MIN_DISTANCE = 80

    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

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
                downX = p1.x
                downY = p1.y
            }
            MotionEvent.ACTION_MOVE -> {
                upX = p1.x
                upY = p1.y
            }
        }
        return false
    }
}