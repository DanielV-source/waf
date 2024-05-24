package com.judc.walkfight

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.math.BigInteger

class ConnectionHelper {
    companion object {
        private const val TAG = "ConnectionHelper"
        private lateinit var activity: LoginActivity

        data class UserData(var username: String, var enemiesDefeated: BigInteger, var bossesDefeated: BigInteger, var score: BigInteger)

        private var email: String = ""

        lateinit var currentUser: UserData

        private const val collection: String = "waf"


        /**
         * Sign out function from Google and Firebase services
         */
        fun signOut() {
            val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(activity)
            if (googleSignInAccount != null) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                val googleSignInClient = GoogleSignIn.getClient(activity, gso)
                googleSignInClient.signOut().addOnCompleteListener() {
                    FirebaseAuth.getInstance().signOut()
                }
            } else {
                // If sign in fails, display an error message
                Log.d(
                    activity.toString(),
                    "Google sign out failed, probably the user was not signed")
                FirebaseAuth.getInstance().signOut()
            }
            this.resetCurrentUser()
        }

        fun loadData() {
            if(this.email.isNotEmpty()) {
                val db = FirebaseFirestore.getInstance()
                db.collection(collection).document(this.email)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val username = document.getString("username") ?: "Player"
                            val enemiesDefeated = document.getLong("enemiesDefeated")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                            val bossesDefeated = document.getLong("bossesDefeated")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                            val score = document.getLong("score")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                            currentUser =
                                UserData(username, enemiesDefeated, bossesDefeated, score)
                        } else {
                            this.resetCurrentUser()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Firestore", "Error getting document", exception)
                        this.resetCurrentUser()
                    }
            }else{
                this.resetCurrentUser()
            }
        }

        fun saveData() {
            if (email.isNotEmpty()) {
                try{
                    runBlocking {
                        val db = FirebaseFirestore.getInstance()
                        val dataMap = mapOf(
                            "username" to currentUser.username,
                            "enemiesDefeated" to currentUser.enemiesDefeated.toLong(),
                            "bossesDefeated" to currentUser.bossesDefeated.toLong(),
                            "score" to currentUser.score.toLong()
                        )

                        db.collection(collection).document(email)
                            .set(dataMap, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d("Firestore", "Document successfully written!")
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Firestore", "Error writing document", exception)
                            }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun getTopRankDocuments(): List<UserData> = runBlocking {
            try {
                val db = FirebaseFirestore.getInstance()
                val documents = db.collection(collection)
                    .orderBy("score", Query.Direction.DESCENDING)
                    .limit(10)
                    .get()
                    .await()

                documents.mapNotNull { document ->
                    val username = document.getString("username") ?: "Player"
                    val enemiesDefeated = document.getLong("enemiesDefeated")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                    val bossesDefeated = document.getLong("bossesDefeated")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                    val score = document.getLong("score")?.let { BigInteger.valueOf(it) } ?: BigInteger.ZERO
                    UserData(username, enemiesDefeated, bossesDefeated, score)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

        fun getUserName(): String {
            return currentUser.username
        }

        fun setUserName(value: String) {
            currentUser.username = value
        }

        fun getEmail(): String {
            return email
        }

        fun setEmail(email: String?) {
            if (email != null) {
                this.email = email
            }
        }

        fun getScore(): Number {
            return currentUser.score
        }

        fun setScore(value: BigInteger) {
            currentUser.score += value
        }

        fun getEnemiesDefeated(): Number {
            return currentUser.enemiesDefeated
        }

        fun addEnemiesDefeated(value: BigInteger) {
            currentUser.enemiesDefeated += value
        }

        fun getBossesDefeated(): Number {
            return currentUser.bossesDefeated
        }

        fun addBossesDefeated(value: BigInteger) {
            currentUser.bossesDefeated += value
        }

        fun setLoginActivity(activity: LoginActivity) {
            this.activity = activity
        }

        fun resetCurrentUser() {
            this.currentUser = UserData("Player", BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO)
        }
    }
}