package com.example.oxeanbits_challenge.permission

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class LocationPermission(
    private val activity: ComponentActivity,
    context: Context
): LocationListener {

    private val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var onPermissionGranted: (() -> Unit)? = null
    private var onPermissionDenied: (() -> Unit)? = null
    private var onUpdateLocationData: ((Double, Double) -> Unit)? = null

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                hasLocationPermission()
                onPermissionGranted?.invoke()
            } else {
                onPermissionDenied?.invoke()
            }
        }

    fun setOnPermissionGrantedCallback(callback: () -> Unit) {
        hasLocationPermission()
        onPermissionGranted = callback
    }

    fun setOnPermissionDeniedCallback(callback: () -> Unit) {
        onPermissionDenied = callback
    }

    fun setOnUpdateLocationDataCallBack(callback: (Double, Double) -> Unit) {
        onUpdateLocationData = callback
    }

    fun requestLocationAccess() {
        if (hasLocationPermission()) {
            onPermissionGranted?.invoke()
        } else {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            requestPermissionLauncher.launch(ACCESS_COARSE_LOCATION)
        }
    }


    private fun hasLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
            activity,
            ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            activity,
            ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
            true
        } else {
            false
        }

    }

    override fun onLocationChanged(location: Location) {
        onUpdateLocationData?.let {
            it(
                location.latitude,
                location.longitude
            )
        }
    }

}