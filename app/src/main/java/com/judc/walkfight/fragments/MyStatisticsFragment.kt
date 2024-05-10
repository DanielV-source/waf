package com.judc.walkfight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment

class MyStatisticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Show user statistics
        val view = inflater.inflate(R.layout.mystats, container, false)

        val globalRankingButton: Button = view.findViewById(R.id.globalrankingmenu)
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