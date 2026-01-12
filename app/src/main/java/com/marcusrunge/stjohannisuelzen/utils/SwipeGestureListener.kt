package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

/**
 * A [View.OnTouchListener] that detects various swipe and scroll gestures.
 *
 * This listener uses a [GestureDetector] to interpret touch events and provides a simple
 * callback interface, [OnSwipeListener], for handling different gestures such as swipes,
 * flings, scrolls, and taps.
 *
 * @param context The context to use for creating the [GestureDetector].
 * @param onSwipeListener The listener to be notified of gesture events.
 */
class SwipeGestureListener(context: Context, private val onSwipeListener: OnSwipeListener?) :
    View.OnTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener())
    private var view: View? = null

    companion object {
        /** The minimum swipe distance in pixels. */
        private const val SWIPE_THRESHOLD = 100

        /** The minimum swipe velocity in pixels per second. */
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    /**
     * Called when a touch event is dispatched to a view.
     *
     * This method passes the touch event to the [GestureDetector] for processing.
     * It also captures the view to which the listener is attached so that `performClick`
     * can be called later for accessibility.
     *
     * @param v The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about the event.
     * @return `true` if the event was handled by the gesture detector, `false` otherwise.
     */
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        view = v
        return gestureDetector.onTouchEvent(event)
    }

    /**
     * An inner class that extends [GestureDetector.SimpleOnGestureListener] to handle
     * callbacks from the [GestureDetector].
     */
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        /**
         * Notified when a scroll gesture occurs.
         *
         * @param e1 The first down motion event that started the scrolling.
         * @param e2 The move motion event that triggered the current onScroll.
         * @param distanceX The distance along the X axis that has been scrolled since the last call to onScroll.
         * @param distanceY The distance along the Y axis that has been scrolled since the last call to onScroll.
         * @return `true` if the event is consumed, `false` otherwise.
         */
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (distanceY > 0) {
                onSwipeListener?.onScrollUp(abs(distanceY).toInt())
            } else {
                onSwipeListener?.onScrollDown(abs(distanceY).toInt())
            }
            return true
        }

        /**
         * Notified when a tap occurs with the down [MotionEvent] that triggered it.
         *
         * @param e The down motion event.
         * @return `true` to indicate that the gesture detector should continue to process this gesture.
         */
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        /**
         * Notified of a fling event when it occurs with the initial on down [MotionEvent]
         * and the matching up [MotionEvent].
         *
         * @param e1 The first down motion event that started the fling.
         * @param e2 The move motion event that triggered the current onFling.
         * @param velocityX The velocity of this fling measured in pixels per second along the x-axis.
         * @param velocityY The velocity of this fling measured in pixels per second along the y-axis.
         * @return `true` if the event is consumed, `false` otherwise.
         */
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 == null) return false

            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x

            return when {
                abs(diffX) > abs(diffY) -> { // Horizontal fling
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeListener?.onSwipeRight(abs(diffX).toInt())
                        } else {
                            onSwipeListener?.onSwipeLeft(abs(diffX).toInt())
                        }
                        true
                    } else {
                        false
                    }
                }
                abs(diffY) > abs(diffX) -> { // Vertical fling
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeListener?.onFlingDown(abs(velocityY).toInt())
                        } else {
                            onSwipeListener?.onFlingUp(abs(velocityY).toInt())
                        }
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }

        /**
         * Notified when a single tap occurs.
         *
         * @param e The down motion event of the single-tap.
         * @return `true` if the event is consumed, `false` otherwise.
         */
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onSwipeListener?.onSingleTapUp(e.x.toInt(), e.y.toInt())
            // Call performClick for accessibility and to trigger standard click behavior.
            view?.performClick()
            return super.onSingleTapUp(e)
        }
    }
}

/**
 * An interface to listen for swipe, fling, scroll, and tap gestures.
 */
interface OnSwipeListener {
    /** Called when a right swipe is detected. */
    fun onSwipeRight(value: Int)

    /** Called when a left swipe is detected. */
    fun onSwipeLeft(value: Int)

    /** Called when an upward fling is detected. */
    fun onFlingUp(velocity: Int)

    /** Called when a downward fling is detected. */
    fun onFlingDown(velocity: Int)

    /** Called when an upward scroll is detected. */
    fun onScrollUp(value: Int)

    /** Called when a downward scroll is detected. */
    fun onScrollDown(value: Int)

    /** Called when a single tap is detected. */
    fun onSingleTapUp(x: Int, y: Int)
}
