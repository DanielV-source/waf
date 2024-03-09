package com.judc.walkfight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class MyStatisticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mystats)

        val globalRankingButton: Button = findViewById(R.id.globalrankingmenu)
        globalRankingButton.setOnClickListener {
            val intentRankingActivity = Intent(this@MyStatisticsActivity, Ranking::class.java)
            startActivity(intentRankingActivity)
            Toast.makeText(this, "Going to Ranking", Toast.LENGTH_LONG).show()
            Log.d(MyStatisticsActivity.TAG, "My Statistics Activity: Intent load Ranking")
        }
        val myStatisticsButton: Button = findViewById(R.id.mainmenu)
        myStatisticsButton.setOnClickListener {
            val intentMyStatisticsActivity = Intent(this@MyStatisticsActivity, InitMenuActivity::class.java)
            startActivity(intentMyStatisticsActivity)
            Toast.makeText(this, "Going to InitMenuActivity", Toast.LENGTH_LONG).show()
            Log.d(MyStatisticsActivity.TAG, "My Statistics Activity: Intent load Init Menu Activity")
        }

    }

    companion object {
        private const val TAG = "MyStatisticsActivity"
    }
}