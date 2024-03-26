package com.judc.walkfight.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.judc.walkfight.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Show profile information
        val view = inflater.inflate(R.layout.profile, container, false)
        Toast.makeText(this.context, "Showing your profile", Toast.LENGTH_LONG).show()
        Log.d(ProfileFragment().tag, "My Profile Fragment: Showing your profile info!")

        return view
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}