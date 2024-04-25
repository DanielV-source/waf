package com.judc.walkfight.maps
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Handle geofence transitions here
        Log.d("GeofenceReceiver", "Geofence transition received!")
    }
}
