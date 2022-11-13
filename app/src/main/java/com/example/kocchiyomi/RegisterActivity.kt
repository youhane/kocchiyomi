package com.example.kocchiyomi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
            val email: String = et_signup_email.text.toString().trim { it <= ' '}
//            val username: String = et_signup_email.text.toString().trim { it <= ' '}
            val password: String = et_signup_password.text.toString().trim { it <= ' '}
            val confirmPassword: String = et_signup_password.text.toString().trim { it <= ' '}

            when {
//                TextUtils.isEmpty(username) -> {
//                    makeToast("Please enter username")
//                }

                TextUtils.isEmpty(email) -> {
                    makeToast("Please enter email")
                }

                isValidEmail(email) -> {
                    makeToast("Please enter valid email")
                }

                TextUtils.isEmpty(password) -> {
                    makeToast("Please enter password")
                }

                TextUtils.isEmpty(confirmPassword) -> {
                    makeToast("Please confirm password")
                }

                TextUtils.equals(password, confirmPassword) -> {
                    makeToast("The password does not match")
                }

                else -> {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {
                                task ->
                                    if(task.isSuccessful){
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        makeToast("You are registered successfully")

                                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", firebaseUser.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        makeToast(task.exception!!.message.toString())
                                    }
                            }
                        )
                }

            }
        }


    }
    private fun isValidEmail(str: CharSequence):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    private fun makeToast(msg: String) {
        Toast.makeText(
            this@RegisterActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}