package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoseWaypointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.loser_waypoint)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@LoseWaypointActivity, InitMenuActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to InitMenuActivity", Toast.LENGTH_LONG).show()
            Log.d(LoseWaypointActivity.TAG, "My Lose Waypoint Activity: Intent load InitMenuActivity")
        }

        val nextWaypointButton: Button = findViewById(R.id.nextwaypoint)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@LoseWaypointActivity, OSMWaypointActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to OSMWaypointActivity", Toast.LENGTH_LONG).show()
            Log.d(LoseWaypointActivity.TAG, "My Lose Waypoint Activity: Intent load OSMWaypointActivity")
        }
    }

    companion object {
        private const val TAG = "LoseWaypointActivity"
    }
}