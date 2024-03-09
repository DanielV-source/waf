package com.judc.walkfight

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast

class InitMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.initmenu)
        val myStatisticsButton: Button = findViewById(R.id.mystatisticsmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@InitMenuActivity, MyStatisticsActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to MyStatisticsActivity", Toast.LENGTH_LONG).show()
            Log.d(InitMenuActivity.TAG, "Init Menu Activity: Intent load MyStatisticsActivity")
        }

    }

    companion object {
        private const val TAG = "InitMenuActivity"
    }
}