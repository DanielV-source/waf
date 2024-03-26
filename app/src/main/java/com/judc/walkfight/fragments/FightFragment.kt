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

class FightFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight, container, false)
        // TODO: Launch Unity activity from here (above an example of intent)
        // val intent = Intent(this.context, WaFGame::class.java)
        // startActivity(intent)
        // TODO: Launch hidden service to catch Unity logs

        // WIN LOGIC
        val winChallengeButton: Button = view.findViewById(R.id.win_fight)
        winChallengeButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, WinnerFragment(), addToBackStack = true,
                tag="WinnerFragment")

            Toast.makeText(this.context, "Going to WinnerFragment", Toast.LENGTH_LONG).show()
            Log.d(FightFragment().tag, "My Fight Fragment: Trying to load WinnerFragment")
        }

        // LOSE LOGIC
        val loseChallengeButton: Button = view.findViewById(R.id.lose_fight)
        loseChallengeButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, LoserFragment(), addToBackStack = true,
                tag="LoserFragment")

            Toast.makeText(this.context, "Going to LoserFragment", Toast.LENGTH_LONG).show()
            Log.d(FightFragment().tag, "My Fight Fragment: Trying to load LoserFragment")
        }

        return view
    }

    companion object {
        fun newInstance() = FightFragment()
    }
}