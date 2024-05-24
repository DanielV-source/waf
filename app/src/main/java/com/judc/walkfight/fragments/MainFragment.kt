package com.judc.walkfight.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.initmenubuttons, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = view.context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val startAdventureButton: Button = view.findViewById(R.id.start_adventure_menu)
        startAdventureButton.setOnClickListener {
            sharedPreferences.edit().putInt("currentPoint", 0).apply()
            // TODO: Restart the actual destination here
            replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true, tag = "OSMFragment")
        }


        val resumeAdventureButton: Button = view.findViewById(R.id.resume_adventure_menu)
        resumeAdventureButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true, tag = "OSMFragment")
        }

        val myStatisticsButton: Button = view.findViewById(R.id.my_statistics_menu)
        myStatisticsButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, MyStatisticsFragment(), addToBackStack = true, tag = "MyStatisticsFragment")
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}