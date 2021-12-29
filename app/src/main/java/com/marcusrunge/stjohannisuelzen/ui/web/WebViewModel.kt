package com.marcusrunge.stjohannisuelzen.ui.web

import android.content.Context
import android.graphics.Point
import android.os.Message
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.core.enums.Scroll
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
    private var _tapUpPoint: Point? = null
    private var _velocityY:Int?=null

    @get:Bindable
    val gestureDetector
        get() = SwipeGestureListener(context, object : OnSwipeListener {
            override fun onSwipeRight(value: Int) {
                core.gestures.swipe.onSwipe(Swipe.Right, value)
            }

            override fun onSwipeLeft(value: Int) {
                core.gestures.swipe.onSwipe(Swipe.Left, value)
            }

            override fun onSwipeUp(diffY: Int, veloY: Int) {
                core.gestures.swipe.onSwipe(Swipe.Up, diffY)
                velocityY=veloY
            }

            override fun onSwipeDown(diffY: Int, veloY: Int) {
                core.gestures.swipe.onSwipe(Swipe.Down, diffY)
                velocityY=-veloY
            }

            override fun onScrollLeft(value: Int) {
                core.gestures.scroll.onScroll(Scroll.Left, value)
            }

            override fun onScrollRight(value: Int) {
                core.gestures.scroll.onScroll(Scroll.Right, value)
            }

            override fun onScrollUp(value: Int) {
                core.gestures.scroll.onScroll(Scroll.Up, value)
            }

            override fun onScrollDown(value: Int) {
                core.gestures.scroll.onScroll(Scroll.Down, value)
            }

            override fun onSingleTapUp(x: Int, y: Int) {
                tapUpPoint = Point(x, y)
            }
        })

    @get:Bindable
    val endpointUrl
        get() = context.getString(R.string.url_stjohannis_uelzen)

    @get:Bindable
    var tapUpPoint: Point?
        get() = _tapUpPoint
        set(value) {
            _tapUpPoint = value
            notifyPropertyChanged(BR.tapUpPoint)
        }

    @get:Bindable
    var velocityY: Int?
        get() = _velocityY
        set(value) {
            _velocityY = value
            notifyPropertyChanged(BR.velocityY)
        }

    override fun updateView(inputMessage: Message) {
        //TODO("Not yet implemented")
    }
}