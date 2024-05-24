package com.judc.walkfight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.judc.walkfight.ConnectionHelper
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment

class MyStatisticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.mystats, container, false)

        val enemiesDefeatedTextView: TextView = view.findViewById(R.id.statistics_enemies_defeated_value)
        enemiesDefeatedTextView.text = ConnectionHelper.getEnemiesDefeated().toString()

        val bossesDefeatedTextView: TextView = view.findViewById(R.id.statistics_bosses_defeated_value)
        bossesDefeatedTextView.text = ConnectionHelper.getBossesDefeated().toString()

        val scoreTextView: TextView = view.findViewById(R.id.statistics_score_value)
        scoreTextView.text = ConnectionHelper.getScore().toString()

        val globalRankingButton: Button = view.findViewById(R.id.global_ranking_menu)
        globalRankingButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, RankingFragment(), addToBackStack = true,
                tag = "RankingFragment")
        }

        val mainMenuButton: Button = view.findViewById(R.id.main_menu)
        mainMenuButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                tag = "MainFragment")
        }

        return view
    }

    companion object {
        fun newInstance() = MyStatisticsFragment()
    }
}