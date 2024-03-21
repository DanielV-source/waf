package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class FightActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fight)

        val winChallengeButton: Button = findViewById(R.id.win_fight)
        winChallengeButton.setOnClickListener {
            val intent = Intent(this@FightActivity, WinnerWaypointActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to WinnerWaypointActivity", Toast.LENGTH_LONG).show()
            Log.d(FightActivity.TAG, "My Fight Activity: Intent load WinnerWaypointActivity")
        }

        val loseChallengeButton: Button = findViewById(R.id.lose_fight)
        loseChallengeButton.setOnClickListener {
            val intent = Intent(this@FightActivity, LoseWaypointActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to LoseWaypointActivity", Toast.LENGTH_LONG).show()
            Log.d(FightActivity.TAG, "My Fight Activity: Intent load LoseWaypointActivity")
        }

    }

    companion object {
        private const val TAG = "FightActivity"
    }
}