package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoseBossActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.loser_boss)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@LoseBossActivity, InitMenuActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to InitMenuActivity", Toast.LENGTH_LONG).show()
            Log.d(LoseBossActivity.TAG, "My Lose Boss Activity: Intent load InitMenuActivity")
        }

        val nextWaypointButton: Button = findViewById(R.id.tryfightagain)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@LoseBossActivity, OSMBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to OSMBossActivity", Toast.LENGTH_LONG).show()
            Log.d(LoseBossActivity.TAG, "My Lose Boss Activity: Intent load OSMBossActivity")
        }

    }

    companion object {
        private const val TAG = "LoseBossActivity"
    }
}