package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class FightBossActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fight)

        val winChallengeButton: Button = findViewById(R.id.win_fight)
        winChallengeButton.setOnClickListener {
            val intent = Intent(this@FightBossActivity, WinnerBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to WinnerBossActivity", Toast.LENGTH_LONG).show()
            Log.d(FightBossActivity.TAG, "My Fight Boss Activity: Intent load WinnerBossActivity")
        }

        val loseChallengeButton: Button = findViewById(R.id.lose_fight)
        loseChallengeButton.setOnClickListener {
            val intent = Intent(this@FightBossActivity, LoseBossActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to LoseBossActivity", Toast.LENGTH_LONG).show()
            Log.d(FightBossActivity.TAG, "My Fight Boss Activity: Intent load LoseBossActivity")
        }

    }

    companion object {
        private const val TAG = "FightBossActivity"
    }
}