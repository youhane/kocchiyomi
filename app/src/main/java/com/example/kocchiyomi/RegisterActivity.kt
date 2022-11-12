package com.example.kocchiyomi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_signup.setOnClickListener {
            when {
                TextUtils.isEmpty(et_signup_username.text.toString().trim() {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter username",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_signup_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_signup_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_signup_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                }

            }
        }


    }
}