package com.example.kocchiyomi.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.ActivityMainBinding
import com.example.kocchiyomi.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.tvSingup.setOnClickListener {

        }

        binding.btnSignin.setOnClickListener {
            val email: String = binding.etSigninEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etSigninPassword.text.toString().trim { it <= ' ' }

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
        super.onViewCreated(view, savedInstanceState)
    }
}