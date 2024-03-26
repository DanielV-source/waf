package com.judc.walkfight.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.judc.walkfight.R

class RankingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Show the current ranking
        return inflater.inflate(R.layout.ranking, container, false)
    }

    companion object {
        fun newInstance() = RankingFragment()
    }
}