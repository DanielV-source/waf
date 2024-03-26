package com.judc.walkfight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.judc.walkfight.MainActivity
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

        val startAdventureButton: Button = view.findViewById(R.id.startadventuremenu)
        startAdventureButton.setOnClickListener {
            // TODO: Restart the actual destination here
            replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true, tag = "OSMFragment")

            Toast.makeText(this.context, "Going to OSMFragment", Toast.LENGTH_LONG).show()
            Log.d(MainFragment().tag, "Main Fragment: Trying to load OSMFragment")
        }


        val resumeAdventureButton: Button = view.findViewById(R.id.resumeadventuremenu)
        resumeAdventureButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, OSMFragment(), addToBackStack = true, tag = "OSMFragment")

            Toast.makeText(this.context, "Going to OSMFragment", Toast.LENGTH_LONG).show()
            Log.d(MainFragment().tag, "Main Fragment: Trying to load OSMFragment")
        }

        val myStatisticsButton: Button = view.findViewById(R.id.mystatisticsmenu)
        myStatisticsButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, MyStatisticsFragment(), addToBackStack = true, tag = "MyStatisticsFragment")

            Toast.makeText(this.context, "Going to MyStatisticsFragment", Toast.LENGTH_LONG).show()
            Log.d(MainFragment().tag, "Main Fragment: Trying to load MyStatisticsFragment")
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}