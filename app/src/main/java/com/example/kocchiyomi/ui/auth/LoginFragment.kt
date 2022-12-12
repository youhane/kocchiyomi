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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.ActivityMainBinding
import com.example.kocchiyomi.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navControl: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        navControl = Navigation.findNavController(view)
        binding.tvSingup.setOnClickListener {
            navControl.navigate(R.id.action_loginFragment3_to_registerFragment2)
        }

        binding.btnSignin.setOnClickListener {
            val email: String = binding.etSigninEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etSigninPassword.text.toString().trim { it <= ' ' }

            when {
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
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            if (it.isSuccessful) {
                                (activity as AuthActivity).makeToast("Login successful")
                                navControl.navigate(R.id.action_loginFragment3_to_libraryFragment)
                            } else {
                                (activity as AuthActivity).makeToast("Login failed, please check your email or password")
                            }
                        }
                }
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }
}