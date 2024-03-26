package com.judc.walkfight

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            handleSignInResult(data)
        }
    }

    /**
     * This function checks if Google and Firebase sign in ends with no errors
     */
    private fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { firebaseAuthWithGoogle(it, account) }
        } catch (e: ApiException) {
            Log.w(TAG, "@string/google_sign_in_failed", e)
            Toast.makeText(this,
                "@string/google_sign_in_failed",
                Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Sign in function from Google and Firebase services
     */
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    /**
     * Sign out function from Google and Firebase services
     */
    private fun signOut() {
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut().addOnCompleteListener(this) {
                FirebaseAuth.getInstance().signOut()
            }
        } else {
            // If sign in fails, display an error message
            Toast.makeText(this,
                "@string/google_sign_in_failed",
                Toast.LENGTH_LONG).show()
            FirebaseAuth.getInstance().signOut()
        }
    }

    /**
     * This is the first function called in this activity. Creates the default layout
     * and display the button for Google Sign-In.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        drawerLayout = findViewById(R.id.drawer_layout)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""

        setSupportActionBar(toolbar)

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.red))

        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener {
            signIn()
        }

        val skipSignInButton: Button = findViewById(R.id.skip_signin)
        skipSignInButton.setOnClickListener {
            skipAccessApp()
        }
    }

    /**
     * This function is executed when activity is loaded
     */
    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and go to the next screen
        accessApp()
    }

    /**
     * Go to next screen if user is not null
     */
    private fun accessApp() {
        val user = mAuth.currentUser
        if (user != null) {
            // TODO: pass the user variable to other screens

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Going to MainActivity", Toast.LENGTH_LONG).show()
            Log.d(TAG, "My Login Activity: Intent load MainActivity")
            // Finish the current activity
            finish()
        }
    }

    /**
     * Go to next screen skipping sign in
     */
    private fun skipAccessApp() {
        // TODO: pass the user variable to other screens

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Going to MainActivity", Toast.LENGTH_LONG).show()
        Log.d(TAG, "My Login Activity: Intent load MainActivity")

        // Finish the current activity
        finish()
    }


    /**
     * Sign in Firebase with Google
     */
    private fun firebaseAuthWithGoogle(idToken: String, account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val email = account.email
        if(email != null) {
            // Create an account in Firebase
            mAuth.createUserWithEmailAndPassword(email, "@id/default_password")
        }
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        user.displayName?.let { Log.w("Warning", it) }
                    }
                    // TODO: pass the user variable to other screens
                    accessApp()
                } else {
                    // If sign in fails, display an error message
                    Toast.makeText(this,
                        "@string/firebase_error",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}