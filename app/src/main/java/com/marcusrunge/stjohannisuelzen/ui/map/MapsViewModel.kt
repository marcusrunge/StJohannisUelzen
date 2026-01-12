package com.marcusrunge.stjohannisuelzen.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.os.Message
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.bases.ViewModelBase
import com.marcusrunge.stjohannisuelzen.utils.BitmapHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * The [ViewModel] for the [MapsFragment].
 *
 * This ViewModel is responsible for preparing and managing the Google Map, handling location
 * updates, and providing map-related data to the UI. It implements [OnMapReadyCallback] to
 * receive the [GoogleMap] instance when it's ready.
 *
 * @property context The application context, injected by Hilt.
 */
@HiltViewModel
class MapsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModelBase(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    private var accuracyCircle: Circle? = null

    // region Bindable Properties

    private var _latitude: String? = null
    private var _longitude: String? = null
    private var _bearing: String? = null
    private var _speed: String? = null

    /** The user's current latitude, formatted as a string. */
    @get:Bindable
    var latitude: String?
        get() = _latitude
        set(value) {
            if (_latitude != value) {
                _latitude = value
                notifyPropertyChanged(BR.latitude)
            }
        }

    /** The user's current longitude, formatted as a string. */
    @get:Bindable
    var longitude: String?
        get() = _longitude
        set(value) {
            if (_longitude != value) {
                _longitude = value
                notifyPropertyChanged(BR.longitude)
            }
        }

    /** The user's current bearing (direction of travel), formatted as a string. */
    @get:Bindable
    var bearing: String?
        get() = _bearing
        set(value) {
            if (_bearing != value) {
                _bearing = value
                notifyPropertyChanged(BR.bearing)
            }
        }

    /** The user's current speed, formatted as a string. */
    @get:Bindable
    var speed: String?
        get() = _speed
        set(value) {
            if (_speed != value) {
                _speed = value
                notifyPropertyChanged(BR.speed)
            }
        }

    // endregion

    // region ViewModel Lifecycle

    /**
     * Handles messages sent from other components, not currently used in this ViewModel.
     */
    override fun updateView(inputMessage: Message) {
        // Not currently implemented
    }

    /**
     * Called when the ViewModel is about to be destroyed.
     * This is where we clean up resources, such as stopping location updates, to prevent leaks.
     */
    override fun onCleared() {
        // Stop location updates if the client has been initialized
        if (::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
        super.onCleared()
    }

    // endregion

    // region Map and Location

    /**
     * The main entry point called when the map is ready to be used.
     * It checks for location permissions and, if granted, proceeds to set up the map UI
     * and start location tracking.
     *
     * @param googleMap The [GoogleMap] instance.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupMapUI(googleMap)
            startLocationUpdates()
        } else {
            // If permission is not granted, set up the map with default view only
            moveCameraToDefaultLocation(googleMap)
        }
    }

    /**
     * Configures the initial UI settings of the map.
     */
    @SuppressLint("MissingPermission")
    private fun setupMapUI(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        drawGeorgswayPolyline(googleMap)
        addChurchMarker(googleMap)
        moveCameraToDefaultLocation(googleMap)
    }

    /**
     * Initializes and starts requesting location updates from the [FusedLocationProviderClient].
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest.Builder(
            PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_SECS
        ).apply {
            setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL_SECS)
            setMaxUpdateDelayMillis(MAX_UPDATE_DELAY_MINS)
        }.build()

        locationCallback = object : LocationCallback() {
            @SuppressLint("DefaultLocale")
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    currentLocation = location
                    updateLocationUI(location)
                    updateAccuracyCircle(location)
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    /**
     * Updates the bindable properties with the latest location data.
     */
    private fun updateLocationUI(location: Location) {
        latitude = String.format("%.4f", location.latitude)
        longitude = String.format("%.4f", location.longitude)
        bearing = "${location.bearing.toInt()}°"
        speed = location.speed.toInt().toString()
    }

    /**
     * Creates or updates a circle on the map representing the user's location accuracy.
     */
    private fun updateAccuracyCircle(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (accuracyCircle == null) {
            accuracyCircle = googleMap.addCircle(
                CircleOptions()
                    .center(latLng)
                    .radius(location.accuracy.toDouble())
                    .fillColor(ContextCompat.getColor(context, R.color.purple_500_translucent))
                    .strokeColor(ContextCompat.getColor(context, R.color.design_default_color_primary))
            )
        } else {
            accuracyCircle?.center = latLng
            accuracyCircle?.radius = location.accuracy.toDouble()
        }
    }

    /**
     * Draws the "Georgsway" path on the map as a polyline.
     */
    private fun drawGeorgswayPolyline(googleMap: GoogleMap) {
        val georgswayLatitudes = context.resources.getStringArray(R.array.georgsway_latitudes)
        val georgswayLongitudes = context.resources.getStringArray(R.array.georgsway_longitudes)
        val polylineOptions = PolylineOptions().clickable(false)
        for (i in georgswayLatitudes.indices) {
            polylineOptions.add(
                LatLng(
                    georgswayLatitudes[i].toDouble(),
                    georgswayLongitudes[i].toDouble()
                )
            )
        }
        polylineOptions.color(ContextCompat.getColor(context, R.color.purple_500))
        googleMap.addPolyline(polylineOptions)
    }

    /**
     * Adds a marker for the St. Johannis church on the map.
     */
    private fun addChurchMarker(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .title(context.resources.getString(R.string.marker_info_title))
                .position(CHURCH_LOCATION)
                .icon(walkIcon)
        )
    }

    /**
     * Moves the map camera to the default starting position.
     */
    private fun moveCameraToDefaultLocation(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_CAMERA_LOCATION, DEFAULT_ZOOM))
    }

    /**
     * Lazily creates a custom bitmap descriptor for the marker icon.
     */
    private val walkIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context, R.color.design_default_color_primary)
        BitmapHelper.vectorToBitmap(context, R.drawable.outline_directions_walk_24, color)
    }

    // endregion

    companion object {
        private val CHURCH_LOCATION = LatLng(53.002251, 10.533597)
        private val DEFAULT_CAMERA_LOCATION = LatLng(52.999412, 10.522050)
        private const val DEFAULT_ZOOM = 14f
        private val UPDATE_INTERVAL_SECS = TimeUnit.SECONDS.toMillis(60)
        private val FASTEST_UPDATE_INTERVAL_SECS = TimeUnit.SECONDS.toMillis(30)
        private val MAX_UPDATE_DELAY_MINS = TimeUnit.MINUTES.toMillis(2)
    }
}
