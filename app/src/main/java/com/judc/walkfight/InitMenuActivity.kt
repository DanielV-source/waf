package com.judc.walkfight

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.widget.Button

class InitMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.initmenu)
        val myStatisticsButton: Button = findViewById(R.id.mystatisticsmenu)
        myStatisticsButton.setOnClickListener {
            val intent = Intent(this@InitMenuActivity, MyStatisticsActivity::class.java)
            startActivity(intent)
        }

    }
}