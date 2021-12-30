package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class SwipeGestureListener(context: Context, val onSwipeListener: OnSwipeListener?) :
    View.OnTouchListener {
    private val gestureDetector: GestureDetector

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (e1?.action == MotionEvent.ACTION_DOWN && e2?.action == MotionEvent.ACTION_MOVE) {
                if (distanceY > 0) {
                    onSwipeListener?.onScrollUp(abs(distanceY).toInt())
                } else {
                    onSwipeListener?.onScrollDown(abs(distanceY).toInt())
                }
                return true
            }
            return false
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                when {
                    abs(diffX) > abs(diffY) -> {
                        if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeListener?.onSwipeRight(abs(diffX).toInt())
                            } else {
                                onSwipeListener?.onSwipeLeft(abs(diffX).toInt())
                            }
                            result = true
                        }
                    }
                    abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD -> {
                        if (diffY > 0) {
                            onSwipeListener?.onFlingDown(abs(velocityY).toInt())
                        } else {
                            onSwipeListener?.onFlingUp(abs(velocityY).toInt())
                        }
                        result = true
                    }
                    diffY > 0 -> {
                        onSwipeListener?.onFlingDown(abs(velocityY).toInt())
                        result = true
                    }
                    else -> {
                        onSwipeListener?.onFlingUp(abs(velocityY).toInt())
                        result = true
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null) {
                onSwipeListener?.onSingleTapUp(e.x.toInt(), e.y.toInt())
            }
            return super.onSingleTapUp(e)
        }
    }
}

interface OnSwipeListener {
    fun onSwipeRight(value: Int)
    fun onSwipeLeft(value: Int)
    fun onFlingUp(velocity: Int)
    fun onFlingDown(velocity: Int)
    fun onScrollUp(value: Int)
    fun onScrollDown(value: Int)
    fun onSingleTapUp(x: Int, y: Int)
}