package com.example.kocchiyomi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_singup.setOnClickListener {

        }

        btn_signin.setOnClickListener {
            val email: String = et_signin_email.text.toString().trim { it <= ' ' }
            val password: String = et_signin_password.text.toString().trim { it <= ' ' }

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

                else -> {
//                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(
//                            OnCompleteListener<AuthResult> { task ->
//                                if (task.isSuccessful) {
//                                    val firebaseUser: FirebaseUser = task.result!!.user!!
//
//                                    (activity as AuthActivity).makeToast("You are registered successfully")
//
//                                    val intent =
//                                        Intent((activity as AuthActivity), MainActivity::class.java)
//                                    intent.flags =
//                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    intent.putExtra("user_id", firebaseUser.uid)
//                                    intent.putExtra("email_id", email)
//                                    startActivity(intent)
//                                } else {
//                                    (activity as AuthActivity).makeToast(task.exception!!.message.toString())
//                                }
//                            }
//                        )
                }
            }

        }
    }
}