package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class LoseWaypointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.loser_waypoint)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@LoseWaypointActivity, InitMenuActivity::class.java)
            startActivity(intent)
        }

        val nextWaypointButton: Button = findViewById(R.id.nextwaypoint)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@LoseWaypointActivity, OSMWaypointActivity::class.java)
            startActivity(intent)
        }

    }
}