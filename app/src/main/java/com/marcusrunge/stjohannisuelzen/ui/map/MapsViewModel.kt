package com.marcusrunge.stjohannisuelzen.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Message
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.utils.BitmapHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModelBase(), OnMapReadyCallback {
    private var _string: String? = null

    @get:Bindable
    var string: String?
        get() = _string
        set(value) {
            _string = value
            notifyPropertyChanged(BR.string)
        }

    override fun updateView(inputMessage: Message) {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            p0.isMyLocationEnabled = true
            p0.uiSettings.isMyLocationButtonEnabled = true
            val georgswayLatitudes = context.resources.getStringArray(R.array.georgsway_latitudes)
            val georgswayLongitudes = context.resources.getStringArray(R.array.georgsway_longitudes)
            val polyline = PolylineOptions().clickable(false)
            for (i in georgswayLatitudes.indices) {
                polyline.points.add(
                    LatLng(
                        georgswayLatitudes[i].toDouble(),
                        georgswayLongitudes[i].toDouble()
                    )
                )
            }
            polyline.color(ContextCompat.getColor(context, R.color.purple_500))
            p0.addPolyline(polyline)
            p0.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(52.999412, 10.522050), 14f))
            p0.addMarker(
                MarkerOptions()
                    .title(context.resources.getString(R.string.marker_info_title))
                    .position(LatLng(53.002251, 10.533597))
                    .icon(bicycleIcon)
            )
            p0.addCircle(
                CircleOptions()
                    .center(LatLng(53.002251, 10.533597))
                    .radius(150.0)
                    .fillColor(ContextCompat.getColor(context, R.color.purple_500_translucent))
                    .strokeColor(
                        ContextCompat.getColor(
                            context,
                            R.color.design_default_color_primary
                        )
                    )
            )
        }
    }

    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context, R.color.design_default_color_primary)
        BitmapHelper.vectorToBitmap(context, R.drawable.outline_directions_walk_24, color)
    }
}