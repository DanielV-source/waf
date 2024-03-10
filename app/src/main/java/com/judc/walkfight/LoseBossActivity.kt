package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class LoseBossActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.loser_boss)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@LoseBossActivity, InitMenuActivity::class.java)
            startActivity(intent)
        }

        val nextWaypointButton: Button = findViewById(R.id.tryfightagain)
        nextWaypointButton.setOnClickListener {
            val intent = Intent(this@LoseBossActivity, OSMBossActivity::class.java)
            startActivity(intent)
        }

    }
}