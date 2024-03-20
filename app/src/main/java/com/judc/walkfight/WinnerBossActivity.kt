package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class WinnerBossActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.winner_boss)
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@WinnerBossActivity, InitMenuActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to InitMenuActivity", Toast.LENGTH_LONG).show()
            Log.d(WinnerBossActivity.TAG, "My Winner Boss Activity: Intent load InitMenuActivity")
        }

    }

    companion object {
        private const val TAG = "WinnerBossActivity"
    }
}