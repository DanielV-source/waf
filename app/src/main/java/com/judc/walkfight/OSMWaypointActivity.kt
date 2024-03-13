package com.judc.walkfight
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import androidx.activity.ComponentActivity
import org.osmdroid.views.MapView

class OSMWaypointActivity : ComponentActivity() {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.osm)

        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        mapView = findViewById(R.id.osmmap)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(43.3713, -8.3960)
        mapController.setCenter(startPoint)

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
    companion object {
        private const val TAG = "OSMWaypointActivity"
    }
}