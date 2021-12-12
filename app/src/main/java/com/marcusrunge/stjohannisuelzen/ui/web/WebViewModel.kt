package com.marcusrunge.stjohannisuelzen.ui.web

import android.content.Context
import android.os.Message
import androidx.databinding.Bindable
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.core.enums.Swipe
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.utils.OnSwipeListener
import com.marcusrunge.stjohannisuelzen.utils.SwipeGestureListener
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    @ApplicationContext private val context: Context, val core: Core
) : ViewModelBase() {

    @get:Bindable
    val gestureDetector
        get() = SwipeGestureListener(context, object : OnSwipeListener {
            override fun onSwipeRight() {
                core.gestures.swipe.onSwipe(Swipe.Right)
            }

            override fun onSwipeLeft() {
                core.gestures.swipe.onSwipe(Swipe.Left)
            }

            override fun onSwipeUp() {
                core.gestures.swipe.onSwipe(Swipe.Up)
            }

            override fun onSwipeDown() {
                core.gestures.swipe.onSwipe(Swipe.Down)
            }
        })

    @get:Bindable
    val endpointUrl
        get() = context.getString(R.string.url_stjohannis_uelzen)

    override fun updateView(inputMessage: Message) {
        //TODO("Not yet implemented")
    }
}