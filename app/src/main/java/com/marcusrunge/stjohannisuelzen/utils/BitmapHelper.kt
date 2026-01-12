package com.marcusrunge.stjohannisuelzen.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * A helper object for working with [Bitmap]s and [BitmapDescriptor]s.
 * This object provides utility functions for bitmap manipulation, such as converting
 * vector drawables to bitmap descriptors for use as map markers.
 */
object BitmapHelper {

    private const val TAG = "BitmapHelper"

    /**
     * Converts a vector drawable to a [BitmapDescriptor] suitable for use as a map marker icon.
     *
     * This method creates a bitmap from the given vector drawable resource, applies a tint color,
     * and then converts the bitmap into a `BitmapDescriptor`. The function is named `vectorToBitmap`
     * for historical reasons, but it returns a `BitmapDescriptor`.
     *
     * Inspired by the ApiDemos on GitHub:
     * https://github.com/googlemaps/android-samples/blob/main/ApiDemos/kotlin/app/src/main/java/com/example/kotlindemos/MarkerDemoActivity.kt
     *
     * @param context The context to use for accessing resources.
     * @param id The resource ID of the vector drawable.
     * @param color The color to tint the vector drawable.
     * @return A [BitmapDescriptor] created from the vector drawable, or a default marker
     *         if the resource cannot be found.
     */
    fun vectorToBitmap(
        context: Context,
        @DrawableRes id: Int,
        @ColorInt color: Int
    ): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
            ?: run {
                Log.e(TAG, "Resource not found for ID: $id")
                return BitmapDescriptorFactory.defaultMarker()
            }

        val bitmap = createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
