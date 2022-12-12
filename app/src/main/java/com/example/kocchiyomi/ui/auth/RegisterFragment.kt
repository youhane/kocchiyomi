package com.example.kocchiyomi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private var callbackFragment: CallbackFragment? = null
    private lateinit var  navControl: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControl = Navigation.findNavController(view)
        binding.tvSignin.setOnClickListener {
            navControl.navigate(R.id.action_registerFragment2_to_loginFragment3)
//            callbackFragment?.changeFragment()
        }

        binding.btnSignup.setOnClickListener {
            val email: String = binding.etSignupEmail.text.toString().trim { it <= ' ' }
            val username: String = binding.etSignupUsername.text.toString().trim { it <= ' '}
            val password: String = binding.etSignupPassword.text.toString().trim { it <= ' ' }
            val confirmPassword: String = binding.etSignupConfirmPassword.text.toString().trim { it <= ' ' }

            when {
                TextUtils.isEmpty(username) -> {
                    (activity as AuthActivity).makeToast("Please enter username")
                }

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
                                    val favorites = arrayListOf<String>()
                                    val user = hashMapOf(
                                        "email" to email,
                                        "username" to username,
                                        "favorites" to favorites
                                    )
                                    FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
                                        Toast.makeText(activity, "Registered successfully", Toast.LENGTH_SHORT).show()
                                        navControl.navigate(R.id.action_registerFragment2_to_loginFragment3)
                                    }

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