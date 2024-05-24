package com.judc.walkfight

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import android.util.Log
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
import com.judc.walkfight.fragments.LoserFragment
import com.judc.walkfight.fragments.MainFragment
import com.judc.walkfight.fragments.ProfileFragment
import com.judc.walkfight.fragments.WinnerFragment
import com.judc.walkfight.utils.GetSensorUtilities
import java.math.BigInteger

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
        if(ConnectionHelper.getEmail().isEmpty()) {
            val profileItem: MenuItem = navView.menu.findItem(R.id.nav_profile)
            profileItem.setVisible(false)
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

                R.id.nav_logout -> {
                    ConnectionHelper.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        val result = intent?.getStringExtra("game_result")
        val ultimates = intent?.getStringExtra("game_ultimates")
        val score = intent?.getStringExtra("game_score")
        val enemyAttacks = intent?.getStringExtra("game_enemyAttacks")

        if (result != null) {
            Log.d("MainActivity", "Game result received: $result")
            handleGameResult(result, ultimates, enemyAttacks, score)
        }

        val sensorChecker = GetSensorUtilities()
        sensorChecker.isSensorAvailable(this, Sensor.TYPE_ACCELEROMETER)
        sensorChecker.isSensorAvailable(this, Sensor.TYPE_GYROSCOPE)
        sensorChecker.hasMicrophone(this)
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


    /**
     * Handles the result of the battle in Unity and replaces with the right fragment
     */
    private fun handleGameResult(result: String, ultimates: String?, enemyAttacks: String?, score: String?) {
        when (result) {
            "win" -> {
                // WIN LOGIC
                val winnerFrag = WinnerFragment()
                if(ultimates != null) {
                    winnerFrag.ultimates = ultimates
                }
                if(enemyAttacks != null) {
                    winnerFrag.enemyAttacks = enemyAttacks
                }
                if(score != null) {
                    winnerFrag.score = score
                    ConnectionHelper.setScore(BigInteger(score))
                }
                replaceFragment(winnerFrag)
            }
            "lose" -> {
                // LOSE LOGIC
                val loserFrag = LoserFragment()
                if(ultimates != null) {
                    loserFrag.ultimates = ultimates
                }
                if(enemyAttacks != null) {
                    loserFrag.enemyAttacks = enemyAttacks
                }
                if(score != null) {
                    loserFrag.score = score
                }
                replaceFragment(loserFrag)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}