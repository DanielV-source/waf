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
import java.math.BigInteger

class WinnerFragment : Fragment() {
    var ultimates: String = "0"
    var enemyAttacks: String = "0"
    var score: String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE)

        val view : View

        if (OSMFragment.isBoss(context)) {
            ConnectionHelper.addBossesDefeated(BigInteger("1"))

            view = inflater.inflate(R.layout.winner_boss, container, false)

            val shareWithFriends: Button = view.findViewById(R.id.share_with_friends)
            shareWithFriends.setOnClickListener {
                shareMessage("Hi, I am playing Walk & Fight! I just reached a score " +
                        "of $score points, in a boss battle. Can you beat me? Play now at http://wafgame.com")
                Log.d(WinnerFragment().tag, "My Winner Fragment: Sharing with friends")
            }

        } else {
            ConnectionHelper.addEnemiesDefeated(BigInteger("1"))

            view = inflater.inflate(R.layout.winner_waypoint, container, false)

            val nextWaypointButton: Button = view.findViewById(R.id.next_waypoint)
            nextWaypointButton.setOnClickListener {
                sharedPreferences.edit().putBoolean("finishFight", true).apply()
                replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true,
                    tag="OSMFragment")

                Log.d(WinnerFragment().tag, "My Winner Fragment: Trying to load OSMFragment")
            }

            val shareWithFriends: Button = view.findViewById(R.id.share_with_friends)
            shareWithFriends.setOnClickListener {

                shareMessage("Hi, I am playing Walk & Fight! I just reached a score " +
                        "of $score points, in a battle. Can you beat me? Play now at http://wafgame.com")
                Log.d(WinnerFragment().tag, "My Winner Fragment: Sharing with friends")
            }
        }

        val mainMenuButton: Button = view.findViewById(R.id.main_menu)
        mainMenuButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, MainFragment(), addToBackStack = true,
                tag="MainFragment")

            Log.d(WinnerFragment().tag, "My Winner Fragment: Trying to load MainFragment")
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

    /**
     * Share message using android sharing options
     */
    private fun shareMessage(message: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    companion object {
        fun newInstance() = WinnerFragment()
    }
}