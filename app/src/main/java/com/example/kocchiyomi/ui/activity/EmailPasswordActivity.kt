package com.example.kocchiyomi.ui.activity
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailPasswordActivity : Activity()
{
    private lateinit var auth: FirebaseAuth

    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null)
        {
            reload();
        }
    }

    private fun createAccount(email: String, password: String)
    {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this)
        {
            task ->
            if (task.isSuccessful)
            {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
            }
            else
            {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun signIn(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this)
        {
            task ->
            if (task.isSuccessful)
            {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
            } else
            {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun sendEmailVerification()
    {
        val user = auth.currentUser!!
        user.sendEmailVerification()
        .addOnCompleteListener(this)
        {
            task ->
            // Email Verification sent
        }
    }


    private fun updateUI(user: FirebaseUser?)
    {

    }

    private fun reload()
    {

    }

    companion object
    {
        private const val TAG = "EmailPassword"
    }
}