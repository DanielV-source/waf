package com.judc.walkfight

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.judc.walkfight.fragments.MainFragment
import com.judc.walkfight.fragments.ProfileFragment
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.judc.walkfight.coroutines.fetchDirections

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Detach first visible fragment when back button is pressed
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStack()
                } else {
                    showExitConfirmationDialog()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        setContentView(R.layout.initmenu)
        drawerLayout = findViewById(R.id.drawer_layout)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.red))

        // Load initial screen layout
        replaceFragment(MainFragment())

        navView = findViewById(R.id.nav_view)
        val menuItem: MenuItem = navView.menu.findItem(R.id.nav_home)
        if (menuItem.itemId == R.id.nav_home && supportFragmentManager.fragments.size > 0) {
            if (supportFragmentManager.getBackStackEntryAt(0).name == "MainFragment")
                menuItem.setVisible(false)
        }
        navView.setNavigationItemSelectedListener { mItem ->
            when (mItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(MainFragment())
                    true
                }

                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        val sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val jsonObject = JsonFileReader.readJsonObject(this, R.raw.coordinates)
        var coordinates: List<String> = emptyList()

        if (jsonObject != null) {
            val pathA = jsonObject.getJSONObject("path_a")
            val start = pathA.getString("start")
            val end = pathA.getString("end")
            coordinates = listOf(start, end)
            println("Reading from SharedPreferences: $coordinates")
            sharedPreferences.edit().putString("coordinate", coordinates.joinToString(",")).apply()
        }

        if (coordinates.size >= 2) {
            fetchDirections(getString(R.string.ors_api_key), coordinates[0], coordinates[1])
        }

        val value = sharedPreferences.getString("coordinate", "default value")
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.exit_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}