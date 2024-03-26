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

class WinnerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Get if it's a boss
        val isBoss = false
        val view : View

        if (isBoss) {
            // TODO: WIN AGAINST BOSS LOGIC HERE

            view = inflater.inflate(R.layout.winner_boss, container, false)

            val shareWithFriends: Button = view.findViewById(R.id.share_with_friends)
            shareWithFriends.setOnClickListener {
                // TODO: Sharing in social networks

                Toast.makeText(this.context, "Sharing with friends!", Toast.LENGTH_LONG).show()
                Log.d(WinnerFragment().tag, "My Winner Fragment: Sharing with friends")
            }

            val myStatisticsButton: Button = view.findViewById(R.id.main_menu)
            myStatisticsButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                    tag = "MainFragment")

                Toast.makeText(this.context, "Going to MainFragment", Toast.LENGTH_LONG).show()
                Log.d(WinnerFragment().tag, "My Winner Fragment: Trying to load MainFragment")
            }

        } else {
            // TODO: WIN AGAINST ENEMY LOGIC HERE

            view = inflater.inflate(R.layout.winner_waypoint, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.nextwaypoint)
            nextWaypointButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="OSMFragment")

                Toast.makeText(this.context, "Going to OSMFragment", Toast.LENGTH_LONG).show()
                Log.d(WinnerFragment().tag, "My Winner Fragment: Trying to load OSMFragment")
            }

            val shareWithFriends: Button = view.findViewById(R.id.share_with_friends)
            shareWithFriends.setOnClickListener {
                // TODO: Sharing in social networks

                Toast.makeText(this.context, "Sharing with friends!", Toast.LENGTH_LONG).show()
                Log.d(WinnerFragment().tag, "My Winner Fragment: Sharing with friends")
            }

            val myStatisticsButton: Button = view.findViewById(R.id.main_menu)
            myStatisticsButton.setOnClickListener {
                replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                    tag="MainFragment")

                Toast.makeText(this.context, "Going to MainFragment", Toast.LENGTH_LONG).show()
                Log.d(WinnerFragment().tag, "My Winner Fragment: Trying to load MainFragment")
            }
        }

        return view
    }

    companion object {
        fun newInstance() = WinnerFragment()
    }
}