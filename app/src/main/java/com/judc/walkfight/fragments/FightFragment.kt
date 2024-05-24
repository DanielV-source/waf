package com.judc.walkfight.fragments

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.judc.walkfight.ConnectionHelper
import com.judc.walkfight.MainActivity
import com.judc.walkfight.R
import com.unity3d.player.UnityPlayerActivity

class FightFragment : Fragment() {
    var dataUltimates = "0"
    var dataEnemyAttacks = "0"
    var dataAccuracy = "0%"
    var dataScore = "0"

    /**
     * Unity Broadcast Receiver, handles all statistical data and the result
     */
    private val unityResultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val ultimates = intent?.getStringExtra("ultimates")
            val enemyAttacks = intent?.getStringExtra("enemyAttacks")
            val accuracy = intent?.getStringExtra("accuracy")
            val score = intent?.getStringExtra("score")
            val result = intent?.getStringExtra("result")

            if (ultimates != null) {
                dataUltimates = ultimates
            }

            if (enemyAttacks != null) {
                dataEnemyAttacks = enemyAttacks
            }

            if (score != null) {
                dataScore = score
            }

            if (accuracy != null) {
                dataAccuracy = "$accuracy%"
            }

            if (result != null) {
                Log.d("UnityResultReceiver", dataUltimates)
                Log.d("UnityResultReceiver", dataEnemyAttacks)
                Log.d("UnityResultReceiver", dataAccuracy)
                Log.d("UnityResultReceiver", dataScore)
                Log.d("UnityResultReceiver", "Result received from Unity: $result")
                val intentMain = Intent(activity, MainActivity::class.java).apply {
                    putExtra("game_result", result)
                    putExtra("game_ultimates", dataUltimates)
                    putExtra("game_enemyAttacks", dataEnemyAttacks)
                    putExtra("game_accuracy", dataAccuracy)
                    putExtra("game_score", dataScore)
                }
                startActivity(intentMain)

                // Closes the actual activity to clean up resources
                activity?.finish()
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight, container, false)

        // Register the local receiver
        val intentFilter = IntentFilter("com.judc.walkfight.GAME_RESULT")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context?.registerReceiver(unityResultReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            context?.registerReceiver(unityResultReceiver, intentFilter)
        }

        var difficulty = 0
        if(OSMFragment.isBoss(context)) {
            difficulty = 1
        }

        val intent = Intent(context, UnityPlayerActivity::class.java)
        intent.putExtra("difficulty", difficulty.toString())
        intent.putExtra("playerName", ConnectionHelper.getUserName())
        startActivity(intent)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.unregisterReceiver(unityResultReceiver)
    }


    companion object {
        fun newInstance() = FightFragment()
    }
}
