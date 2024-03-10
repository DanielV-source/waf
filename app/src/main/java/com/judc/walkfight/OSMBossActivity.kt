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

class OSMBossActivity : ComponentActivity() {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.osm)

        // Initialize the map view
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        mapView = findViewById(R.id.osmmap)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Set initial map center and zoom level
        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(43.3713, -8.3960)
        mapController.setCenter(startPoint)

        val winChallengeButton: Button = findViewById(R.id.winchallenge)
        winChallengeButton.setOnClickListener {
            val intent = Intent(this@OSMBossActivity, WinnerBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to WinnerBossActivity", Toast.LENGTH_LONG).show()
            Log.d(OSMBossActivity.TAG, "My OSM Boss Activity: Intent load WinnerBossActivity")
        }

        val loseChallengeButton: Button = findViewById(R.id.losechallenge)
        loseChallengeButton.setOnClickListener {
            val intent = Intent(this@OSMBossActivity, LoseBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to LoseBossActivity", Toast.LENGTH_LONG).show()
            Log.d(OSMBossActivity.TAG, "My OSM Boss Activity: Intent load LoseBossActivity")
        }
    }
    companion object {
        private const val TAG = "OSMBossActivity"
    }
}