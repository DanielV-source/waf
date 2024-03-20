package com.judc.walkfight.maps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat

class OSMLocationHandler(private val context: Context) : LocationListener {

        private var locationManager: LocationManager? = null
        private var locationListener: LocationListener? = null

        init {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        fun requestLocationUpdates(listener: LocationListener) {
            this.locationListener = listener
            if (checkLocationPermission()) {
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            }
        }

        fun removeLocationUpdates() {
            locationManager?.removeUpdates(this)
        }

        override fun onLocationChanged(location: Location) {
            locationListener?.onLocationChanged(location)
        }

        private fun checkLocationPermission(): Boolean {
            return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
}