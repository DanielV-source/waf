package com.judc.walkfight.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile, container, false)

        val usernameTextView: TextView = view.findViewById(R.id.profile_username_value)
        if(ConnectionHelper.getUserName().isNotEmpty()) {
            usernameTextView.text = ConnectionHelper.getUserName()
        }

        val saveButton: Button = view.findViewById(R.id.profile_button_save)
        saveButton.setOnClickListener {
            if(usernameTextView.text.isNotEmpty()) {
                ConnectionHelper.setUserName(usernameTextView.text.toString())
                Toast.makeText(view.context, "Username updated!", Toast.LENGTH_LONG).show()
                // Save data
                CoroutineScope(Dispatchers.Main).launch {
                    ConnectionHelper.saveData()
                }
            }
        }

        if(ConnectionHelper.getEmail().isNotEmpty()) {
            val emailTextView: TextView = view.findViewById(R.id.profile_email_value)
            emailTextView.text = ConnectionHelper.getEmail()
        }else{
            usernameTextView.isEnabled = false
            usernameTextView.inputType = InputType.TYPE_NULL
            usernameTextView.isFocusable = false
        }

        return view
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}