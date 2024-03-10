package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class WinnerWaypointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.winner_waypoint)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@WinnerWaypointActivity, InitMenuActivity::class.java)
            startActivity(intent)
        }

        val nextWaypointButton: Button = findViewById(R.id.nextwaypoint)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@WinnerWaypointActivity, OSMBossActivity::class.java)
            startActivity(intent)
        }

    }
}