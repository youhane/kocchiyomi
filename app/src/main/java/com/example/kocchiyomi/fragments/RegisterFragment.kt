package com.example.kocchiyomi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun init(view: View){
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    private fun registerEvents(){
        binding.gotoLogin.setOnClickListener{
            navControl.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerBtn.setOnClickListener{
            val email = binding.emailInput.text.toString()
            val pass = binding.passInput.text.toString().trim()
            val reEnterPass = binding.passReInput.text.toString().trim()
            val username = binding.nameInput.text.toString().trim()
            val favorites = arrayListOf<String>()

            if(email.isNotEmpty() && pass.isNotEmpty() && reEnterPass.isNotEmpty()){
                if(pass == reEnterPass){
                    binding.progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val user = hashMapOf(
                                "email" to email,
                                "username" to username,
                                "favorites" to favorites
                            )
                            firestore.collection("users").add(user).addOnSuccessListener {
                                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                                navControl.navigate(R.id.action_registerFragment_to_loginFragment)
                            }.addOnFailureListener { e ->
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Error: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}