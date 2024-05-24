package com.judc.walkfight.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.judc.walkfight.ConnectionHelper
import com.judc.walkfight.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RankingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Show the current ranking
        val view = inflater.inflate(R.layout.ranking, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            val userDataList = ConnectionHelper.getTopRankDocuments()

            updateTextViews(view, userDataList)
        }
        return view
    }

    @SuppressLint("DiscouragedApi")
    private fun updateTextViews(view: View, userDataList: List<ConnectionHelper.Companion.UserData>) {
        for (i in userDataList.indices) {
            val userData = userDataList[i]
            val textViewId = resources.getIdentifier("player${i + 1}", "id", activity?.packageName)
            val textView = view.findViewById<TextView>(textViewId)
            textView?.text = getString(R.string.player_score, i + 1, userData.username, userData.score)
        }
    }

    companion object {
        fun newInstance() = RankingFragment()
    }
}