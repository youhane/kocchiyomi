package com.example.kocchiyomi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kocchiyomi.ui.auth.AuthActivity
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentLoginBinding
import com.example.kocchiyomi.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private var callbackFragment: CallbackFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignin.setOnClickListener {
            Log.d("d", "clcik")
            callbackFragment?.changeFragment()
        }

        binding.btnSignup.setOnClickListener {
            val email: String = binding.etSignupEmail.text.toString().trim { it <= ' ' }

            val password: String = binding.etSignupPassword.text.toString().trim { it <= ' ' }
            val confirmPassword: String = binding.etSignupConfirmPassword.text.toString().trim { it <= ' ' }

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