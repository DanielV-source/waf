package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import androidx.activity.ComponentActivity
import com.judc.walkfight.maps.OSMLocationHandler
import com.judc.walkfight.maps.OSMMapHandler

class OSMWaypointActivity : ComponentActivity() {
    private lateinit var mapView: MapView
    private lateinit var osmLocationHandler: OSMLocationHandler
    private lateinit var osmMapHandler: OSMMapHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.osm)

        mapView = findViewById(R.id.osmmap)
        osmLocationHandler = OSMLocationHandler(this)
        osmMapHandler = OSMMapHandler(this, mapView)

        val currentLocationMarker = osmMapHandler.getCurrentLocationMarker()

        // Set the current location when this activity starts
        osmLocationHandler.requestLocationUpdates { location ->
            osmMapHandler.updateMapLocation(
                location.latitude,
                location.longitude
            )
            currentLocationMarker.position = GeoPoint(location.latitude, location.longitude)
        }

        val winChallengeButton: Button = findViewById(R.id.winchallenge)
        winChallengeButton.setOnClickListener {
            val intent = Intent(this@OSMWaypointActivity, WinnerWaypointActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to WinnerWaypointActivity", Toast.LENGTH_LONG).show()
            Log.d(OSMWaypointActivity.TAG, "My OSM Waypoint Activity: Intent load WinnerWaypointActivity")
        }

        val loseChallengeButton: Button = findViewById(R.id.losechallenge)
        loseChallengeButton.setOnClickListener {
            val intent = Intent(this@OSMWaypointActivity, LoseWaypointActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to LoseWaypointActivity", Toast.LENGTH_LONG).show()
            Log.d(OSMWaypointActivity.TAG, "My OSM Waypoint Activity: Intent load LoseWaypointActivity")
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        osmLocationHandler.removeLocationUpdates()
    }

    companion object {
        private const val TAG = "OSMWaypointActivity"
    }
}