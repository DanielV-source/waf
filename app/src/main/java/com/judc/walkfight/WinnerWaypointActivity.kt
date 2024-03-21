package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class WinnerWaypointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.winner_waypoint)
        val myStatisticsButton: Button = findViewById(R.id.main_menu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@WinnerWaypointActivity, InitMenuActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to InitMenuActivity", Toast.LENGTH_LONG).show()
            Log.d(WinnerWaypointActivity.TAG, "My Winner Waypoint Activity: Intent load InitMenuActivity")
        }

        val shareWithFriends: Button = findViewById(R.id.share_with_friends)
        shareWithFriends.setOnClickListener {
            Toast.makeText(this, "Sharing with friends", Toast.LENGTH_LONG).show()
            Log.d(WinnerWaypointActivity.TAG, "My Winner Waypoint Activity: Sharing with friends")
        }

        val nextWaypointButton: Button = findViewById(R.id.nextwaypoint)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@WinnerWaypointActivity, OSMBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to OSMBossActivity", Toast.LENGTH_LONG).show()
            Log.d(WinnerWaypointActivity.TAG, "My Winner Waypoint Activity: Intent load OSMBossActivity")
        }

    }

    companion object {
        private const val TAG = "WinnerWaypointActivity"
    }
}