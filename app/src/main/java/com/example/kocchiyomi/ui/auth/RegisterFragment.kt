package com.example.kocchiyomi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kocchiyomi.ui.auth.AuthActivity
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var callbackFragment: CallbackFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_signin.setOnClickListener {
            Log.d("d", "clcik")
            callbackFragment?.changeFragment()
        }

        btn_signup.setOnClickListener {
            val email: String = et_signup_email.text.toString().trim { it <= ' ' }
//            val username: String = et_signup_email.text.toString().trim { it <= ' '}
            val password: String = et_signup_password.text.toString().trim { it <= ' ' }
            val confirmPassword: String = et_signup_password.text.toString().trim { it <= ' ' }

            when {
//                TextUtils.isEmpty(username) -> {
//                    makeToast("Please enter username")
//                }

                TextUtils.isEmpty(email) -> {
                    (activity as AuthActivity).makeToast("Please enter email")
                }

                !(activity as AuthActivity).isValidEmail(email) -> {
                    (activity as AuthActivity).makeToast("Please enter valid email")
                }

                TextUtils.isEmpty(password) -> {
                    (activity as AuthActivity).makeToast("Please enter password")
                }

                TextUtils.isEmpty(confirmPassword) -> {
                    (activity as AuthActivity).makeToast("Please confirm password")
                }

                !TextUtils.equals(password, confirmPassword) -> {
                    (activity as AuthActivity).makeToast("The password does not match")
                }

                else -> {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    (activity as AuthActivity).makeToast("You are registered successfully")

                                    val intent =
                                        Intent((activity as AuthActivity), MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                } else {
                                    (activity as AuthActivity).makeToast(task.exception!!.message.toString())
                                }
                            }
                        )
                }
            }

        }
    }
    public fun setCallbackFragment(callbackFragment: CallbackFragment){
        this.callbackFragment = callbackFragment
    }

//    private fun isValidEmail(str: CharSequence): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(str).matches()
//    }

//    private fun makeToast(msg: String) {
//        Toast.makeText(
//            (activity as AuthActivity),
//            msg,
//            Toast.LENGTH_SHORT
//        ).show()
//    }
}