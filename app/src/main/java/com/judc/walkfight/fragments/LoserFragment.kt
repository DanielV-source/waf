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

class LoserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Get if it's a boss
        val isBoss = false
        val view : View

        if(isBoss) {
            // TODO: DEFEAT AGAINST BOSS LOGIC HERE

            view = inflater.inflate(R.layout.loser_boss, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.tryfightagain)
            nextWaypointButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="LoserFragment")

                Toast.makeText(this.context, "Going to OSMBossActivity", Toast.LENGTH_LONG).show()
                Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load OSMActivity")
            }

            val mainMenuButton: Button = view.findViewById(R.id.main_menu)
            mainMenuButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                    tag="MainFragment")

                Toast.makeText(this.context, "Going to MainFragment", Toast.LENGTH_LONG).show()
                Log.d(LoserFragment().tag, "My Loser Fragment: Intent load MainFragment")
            }

        }else{
            // TODO: DEFEAT AGAINST ENEMY LOGIC HERE

            view = inflater.inflate(R.layout.loser_waypoint, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.nextwaypoint)
            nextWaypointButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="OSMFragment")

                Toast.makeText(this.context, "Going to OSMFragment", Toast.LENGTH_LONG).show()
                Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load OSMFragment")
            }

            val mainMenuButton: Button = view.findViewById(R.id.main_menu)
            mainMenuButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                    tag="MainFragment")

                Toast.makeText(this.context, "Going to MainFragment", Toast.LENGTH_LONG).show()
                Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load MainFragment")
            }
        }

        return view
    }

    companion object {
        fun newInstance() = LoserFragment()
    }
}