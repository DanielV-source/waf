package com.judc.walkfight.fragments

import android.content.Context
import android.content.Intent
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
import com.judc.walkfight.LoginActivity
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoserFragment : Fragment() {
    var ultimates: String = "0"
    var enemyAttacks: String = "0"
    var score: String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val view : View

        if(OSMFragment.isBoss(context)) {

            view = inflater.inflate(R.layout.loser_boss, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.try_fight_again)
            nextWaypointButton.setOnClickListener {
                sharedPreferences.edit().putBoolean("finishFight", true).apply()
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="LoserFragment")

                Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load OSMActivity")
            }

        }else{

            view = inflater.inflate(R.layout.loser_waypoint, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.nextwaypoint)
            nextWaypointButton.setOnClickListener {
                sharedPreferences.edit().putBoolean("finishFight", true).apply()
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="OSMFragment")

                Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load OSMFragment")
            }

        }

        val mainMenuButton: Button = view.findViewById(R.id.main_menu)
        mainMenuButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                tag="MainFragment")

            Log.d(LoserFragment().tag, "My Loser Fragment: Trying to load MainFragment")
        }

        val ultimatesTextView: TextView = view.findViewById(R.id.fight_ultimates_value)
        ultimatesTextView.text = ultimates

        val enemyAttacksTextView: TextView = view.findViewById(R.id.fight_enemy_attacks_value)
        enemyAttacksTextView.text = enemyAttacks

        val scoreTextView: TextView = view.findViewById(R.id.fight_score_value)
        scoreTextView.text = getString(R.string.points, score)

        // Trial mode
        if(ConnectionHelper.getEmail().isEmpty()) {
            ConnectionHelper.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(view.context, "You are in trial mode, sign in to save your data", Toast.LENGTH_LONG).show()
            activity?.finish()
        }

        // Save data
        CoroutineScope(Dispatchers.Main).launch {
            ConnectionHelper.saveData()
        }

        return view
    }

    companion object {
        fun newInstance() = LoserFragment()
    }
}